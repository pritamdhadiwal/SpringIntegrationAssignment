package com.assignment.springintegration.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.assignment.springintegration.file.JsonToCSVFileTransformer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/ApplicationContext.xml"})
@Configuration
public class SpringIntegrationApplicationTests {
	@Value("${csv.dir}")
	private String csvFileDirectory;

	public String getCsvFileDirectory() {
		return csvFileDirectory;
	}

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	final String sourceFileName = getCsvFileDirectory() + "/output.csv";

	@Test
	public void contextLoads() {
	}

	@Test
	public void testJsonToCsvConvertor() throws IOException {
		JsonToCSVFileTransformer jsonToCSVFile = (JsonToCSVFileTransformer) context.getBean("transformer");

		assertNotNull(context.getBean("transformer"));

		try {
			final File file = new File(sourceFileName);

			Assert.isTrue(file.exists(), String.format("File '%s' does not exist.", sourceFileName));
			// jsonToCSVFile.handleFile(file);
			System.out.println(String.format("Successfully parsed '%s' file to a " + "to location under the name '%s'",
					sourceFileName, ""));
		} finally {
			// SftpTestUtils.cleanUp(template, destinationFileName);
			context.close();
		}

	}

}
