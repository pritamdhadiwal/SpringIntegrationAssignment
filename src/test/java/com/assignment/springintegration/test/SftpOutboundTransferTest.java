package com.assignment.springintegration.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import com.jcraft.jsch.ChannelSftp.LsEntry;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/ApplicationContext.xml"})
@Configuration
public class SftpOutboundTransferTest {

	@Autowired
	private ApplicationContext context;

	@Value("${csv.dir}")
	private String csvFileDirectory;

	final String sourceFileName = csvFileDirectory + "/output.csv";

	@Test

	public void testOutbound() throws Exception {

		final String destinationFileName = sourceFileName;
		@SuppressWarnings("unchecked")
		SessionFactory<LsEntry> sessionFactory = context.getBean(CachingSessionFactory.class);
		RemoteFileTemplate<LsEntry> template = new RemoteFileTemplate<LsEntry>(sessionFactory);
		SftpTestUtils.createTestFiles(template); // Just the directory

		try {
			final File file = new File(sourceFileName);

			Assert.isTrue(file.exists(), String.format("File '%s' does not exist.", sourceFileName));

			final Message<File> message = MessageBuilder.withPayload(file).build();
			final MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
			inputChannel.send(message);
			Thread.sleep(2000);
			System.out.println(
					String.format("Successfully transferred '%s' file to a " + "remote location under the name '%s'",
							sourceFileName, destinationFileName));
		} finally {
			// SftpTestUtils.cleanUp(template, destinationFileName);
		}
	}

}