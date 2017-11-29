package com.assignment.springintegration;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import com.assignment.springintegration.file.JsonToCSVFileTransformer;




@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class SpringIntegrationApplication implements ApplicationRunner {

	@Value("${sftp.host}")
	private String sftpHost;

	@Value("${sftp.port:0}")
	private int sftpPort;

	@Value("${sftp.username}")
	private String sftpUser;

	@Value("${sftp.password}")
	private String sftpPassword;

	@Value("${sftp.dir}")
	private String sftpDirectory;

	@Value("${json.dir}")
	private String JsonFileDirectory;

	private CompositeFileListFilter<File> filters;

	@Value("${sftp.dir:/tmp/sftptest/}")
	private String sftpRemoteDirectory;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws InterruptedException, ExecutionException {

	}

	@Bean
	public IntegrationFlow processFileFlow() {
		return IntegrationFlows.from("jsonfilesIn").handle("jsonToCSVFileTransformer", "handleFile").get();
	}

	@InboundChannelAdapter(value = "jsonfilesIn", poller = @Poller(fixedDelay = "5000"))
	public File fileReadingMessageSource() {
		filters = new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter("*.json"));
		File source = new File(JsonFileDirectory + "/school.json");
		return source;
	}

	@Bean
	public JsonToCSVFileTransformer jsonToCSVFileTransformer() {
		return new JsonToCSVFileTransformer();
	}

	@Bean
	public MessageChannel jsonfilesIn() {
		return new DirectChannel();
	}

	@Bean
	public DefaultSftpSessionFactory sftpSessionFactory() {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
		Properties prop = new Properties();
		prop.put("poolSize", "50");
		prop.put("sessionWaitTimeout", "5000");
		prop.put("StrictHostKeyChecking", "no");
		factory.setHost("localhost");
		factory.setPort(0);
		factory.setUser("user");
		factory.setPassword("password");
		factory.setSessionConfig(prop);
		factory.setAllowUnknownKeys(true);
		return factory;
	}

	/*
	 * @Bean public IntegrationFlow sftpPutFlow() {
	 * System.out.println("In sftpPutFlow flow:::::::::::"); return
	 * IntegrationFlows.from("sftpPutInputChannel") .handleWithAdapter(h ->
	 * h.sftpGateway(sftpSessionFactory(),
	 * AbstractRemoteFileOutboundGateway.Command.PUT, "payload")
	 * .options(AbstractRemoteFileOutboundGateway.Option.RECURSIVE)
	 * .regexFileNameFilter("(subSftpSource|.*.csv)") .localDirectory(new
	 * File("/home/promethean/Pritam/SpringAssignment/src/test/write/output.csv"
	 * ))
	 * .localFilenameExpression("#remoteFileName.replaceFirst('sftpSource', 'localTarget')"
	 * )) .channel("jsonfilesIn") .get(); }
	 */

	@Bean
	@ServiceActivator(inputChannel = "toSftpChannel")
	public SftpMessageHandler handler() {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
		handler.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory));
		handler.setFileNameGenerator(new FileNameGenerator() {

			@Override
			public String generateFileName(Message<?> message) {
				if (message.getPayload() instanceof File) {
					return ((File) message.getPayload()).getName();
				} else {
					throw new IllegalArgumentException("File expected as payload.");
				}
			}

		});
		return handler;
	}

	@MessagingGateway
	public interface UploadGateway {

		@Gateway(requestChannel = "toSftpChannel")
		void upload(File file);

	}

	@Bean
	public MessageChannel toSftpChannel() {
		return new DirectChannel();
	}

}
