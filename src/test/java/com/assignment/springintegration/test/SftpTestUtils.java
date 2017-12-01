package com.assignment.springintegration.test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.SessionCallback;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SftpTestUtils {
	public static <LsEntry> void createTestFiles(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (Objects.nonNull(template)) {
			final ByteArrayInputStream stream = new ByteArrayInputStream("foo".getBytes());
			template.execute((SessionCallback<LsEntry, Void>) session -> {

				try {
					session.mkdir("si.sftp.sample");
				} catch (Exception e) {
					assertThat(e.getMessage(), containsString("failed to create"));
				}
				for (int i = 0; i < fileNames.length; i++) {
					stream.reset();
					session.write(stream, "sftpSample/" + fileNames[i]);
				}
				return null;
			});
		}
	}

	public static <LsEntry> void cleanUp(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (Objects.nonNull(template)) {
			template.execute((SessionCallback<LsEntry, Void>) session -> {
				for (int i = 0; i < fileNames.length; i++) {
					try {
						session.remove("si.sftp.sample/" + fileNames[i]);
					} catch (IOException e) {
					}
				}

				// should be empty
				session.rmdir("si.sftp.sample");
				return null;
			});
		}
	}

	public static <LsEntry> boolean fileExists(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (Objects.nonNull(template)) {
			return template.execute(session -> {
				ChannelSftp channel = (ChannelSftp) session.getClientInstance();
				for (int i = 0; i < fileNames.length; i++) {
					try {
						SftpATTRS stat = channel.stat("si.sftp.sample/" + fileNames[i]);
						if (Objects.nonNull(stat)) {
							System.out.println("stat returned null for " + fileNames[i]);
							return false;
						}
					} catch (SftpException e) {
						System.out.println("Remote file not present: " + e.getMessage() + ": " + fileNames[i]);
						return false;
					}
				}
				return true;
			});
		} else {
			return false;
		}
	}

}
