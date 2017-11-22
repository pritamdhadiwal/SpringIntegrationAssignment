package com.assignment.springintegration.sftp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.scp.ScpCommandFactory;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;


public class EmbeddedSftpServer implements InitializingBean, SmartLifecycle {
	/**
	 * Let OS to obtain the proper port
	 */
	public static final int PORT = 0;

	private final SshServer server = SshServer.setUpDefaultServer();

	private volatile int port;

	private volatile boolean running;

	private DefaultSftpSessionFactory defaultSftpSessionFactory;

	public void setPort(int port) {
		this.port = port;
	}

	public void setDefaultSftpSessionFactory(DefaultSftpSessionFactory defaultSftpSessionFactory) {
		this.defaultSftpSessionFactory = defaultSftpSessionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.server.setPort(0);
		this.server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
		this.server.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
		this.server.setCommandFactory(new ScpCommandFactory());
		this.server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
		this.server.setPasswordAuthenticator(new PasswordAuthenticator() {

			public boolean authenticate(String username, String password, ServerSession session) {

				// TODO Auto-generated method stub

				return true;

			}

		});

		final String pathname = System.getProperty("java.io.tmpdir") + File.separator + "sftptest" + File.separator;
		new File(pathname).mkdirs();
		server.setFileSystemFactory(new VirtualFileSystemFactory(Paths.get(pathname)));
	}

	public void setHomeFolder(Path path) {
		server.setFileSystemFactory(new VirtualFileSystemFactory(path));
	}

	@Override
	public boolean isAutoStartup() {
		return PORT == this.port;
	}

	@Override
	public int getPhase() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void start() {
		try {
			this.server.start();
			this.defaultSftpSessionFactory.setPort(this.server.getPort());
			this.running = true;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	@Override
	public void stop() {
		if (this.running) {
			try {
				server.stop(true);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			} finally {
				this.running = false;
			}
		}
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}
