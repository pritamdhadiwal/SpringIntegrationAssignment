package com.assignment.springintegration.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.assignment.springintegration.SpringIntegrationApplication;
import com.assignment.springintegration.SpringIntegrationApplication.UploadGateway;
import com.assignment.springintegration.sftp.EmbeddedSftpServer;



@SpringBootTest
@TestPropertySource(properties = { "sftp.port = 10022" })
@ContextConfiguration(classes = SpringIntegrationApplication.class, loader = AnnotationConfigContextLoader.class)
public class SftpOutboundTransferTest {

	
	private UploadGateway gateway;

	@Value("${csv.dir}")
	private String csvFileDirectory;

	final String sourceFileName = csvFileDirectory + "/output.csv";

	private static EmbeddedSftpServer server;

	private static Path sftpFolder;

	@BeforeClass
	public static void startServer() throws Exception {
		server = new EmbeddedSftpServer();
		server.setPort(10022);
		sftpFolder = Files.createTempDirectory("Pritam");
		server.afterPropertiesSet();
		server.setHomeFolder(sftpFolder);
		// Starting SFTP
		if (!server.isRunning()) {
			server.start();
		}
	}

	@Before
	@After
	public void cleanSftpFolder() throws IOException {
		Files.walk(sftpFolder).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
	}

	@Test
	public void testOutbound() throws Exception {

		final String destinationFileName = sourceFileName;

		try {
			final File file = new File(sourceFileName);

			Path tempFile = Files.createTempFile("UPLOAD_TEST", ".csv");

			// Prerequisites
			assertEquals(0, Files.list(sftpFolder).count());

			// test phase
			gateway.upload(tempFile.toFile());

			// Validation phase
			List<Path> paths = Files.list(sftpFolder).collect(Collectors.toList());
			assertEquals(1, paths.size());
			assertEquals(tempFile.getFileName(), paths.get(0).getFileName());
		} finally {
			// SftpTestUtils.cleanUp(template, destinationFileName);
		}
	}

	@AfterClass
	public static void stopServer() {
		if (server.isRunning()) {
			server.stop();
		}
	}
}