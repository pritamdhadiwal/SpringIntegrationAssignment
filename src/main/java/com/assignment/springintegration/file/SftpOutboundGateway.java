package com.assignment.springintegration.file;

import java.io.File;

import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class SftpOutboundGateway<LsEntry> {
	
	@Test
	public void testOutbound() throws Exception{

		final String sourceFileName = "README.md";
		final String destinationFileName = sourceFileName +"_foo";

		final ClassPathXmlApplicationContext ac =
			new ClassPathXmlApplicationContext("/META-INF/spring/integration/fileCopyApplicationContext.xml",
					SftpOutboundGateway.class);
		@SuppressWarnings("unchecked")
		SessionFactory<LsEntry> sessionFactory = ac.getBean(CachingSessionFactory.class);
		RemoteFileTemplate<LsEntry> template = new RemoteFileTemplate<LsEntry>(sessionFactory);
		SftpTestUtils.createTestFiles(template); // Just the directory

		try {
			final File file = new File(sourceFileName);

			Assert.isTrue(file.exists(), String.format("File '%s' does not exist.", sourceFileName));

			final Message<File> message = MessageBuilder.withPayload(file).build();
			final MessageChannel inputChannel = ac.getBean("inputChannel", MessageChannel.class);

			inputChannel.send(message);
			Thread.sleep(2000);

			Assert.isTrue(SftpTestUtils.fileExists(template, destinationFileName),
					String.format("File '%s' does not exist.", destinationFileName));

			System.out.println(String.format("Successfully transferred '%s' file to a " +
					"remote location under the name '%s'", sourceFileName, destinationFileName));
		}
		finally {
			SftpTestUtils.cleanUp(template, destinationFileName);
			ac.close();
		}
	}
}
