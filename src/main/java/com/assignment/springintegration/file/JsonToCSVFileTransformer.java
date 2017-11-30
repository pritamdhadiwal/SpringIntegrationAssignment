package com.assignment.springintegration.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.assignment.springintegration.SpringIntegrationApplication.UploadGateway;
import com.assignment.springintegration.constant.CSVFileConstants;
import com.assignment.springintegration.pojo.CSVFileData;
import com.assignment.springintegration.pojo.SchoolData;
import com.assignment.springintegration.pojo.Schools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;


@Component("jsonToCSVFileTransformer")
public class JsonToCSVFileTransformer {

	@Value("${csv.dir}")
	private String csvFileDirectory;

	@Value("${json.dir}")
	private String JsonFileDirectory;
	
	@Autowired
    private UploadGateway gateway;
 
	
    
	public void handleFile(File input) throws IOException {

		final String sourceFileName = csvFileDirectory + "/output.csv";
		final File file = new File(sourceFileName);
		try {
			/* conversion */
			ObjectMapper objectMapper = new ObjectMapper();
			Schools schools = objectMapper.readValue(input, Schools.class);
			List<CSVFileData> list = buildCsvFile(schools);
			System.out.println("Now as CSV: ");
			// initialize and configure the mapper
			CsvMapper csvMapper = new CsvMapper();
			// initialize the schema
			CsvSchema schema = CsvSchema.builder().setColumnSeparator('\t')
					.addColumn(CSVFileConstants.CSV_SCHOOLCODE, CsvSchema.ColumnType.NUMBER)
					.addColumn(CSVFileConstants.CSV_SCHOOLNAME).addColumn(CSVFileConstants.CSV_ADDRESS1)
					.addColumn(CSVFileConstants.CSV_ADDRESS2).addColumn(CSVFileConstants.CSV_ADDRESS3)
					.addColumn(CSVFileConstants.CSV_CITY).addColumn(CSVFileConstants.CSV_POSTAL_CODE)
					.addColumn(CSVFileConstants.CSV_STATE_CODE).addColumn(CSVFileConstants.CSV_ACTIVE)
					.addColumn(CSVFileConstants.CSV_COUNTRY_CODE).build().withHeader();
			// map the bean with our schema for the writer
			ObjectWriter writer = csvMapper.writerFor(CSVFileData.class).with(schema);
			// we write the list of objects
			writer.writeValues(file).writeAll(list);
			/* to push file in SFTP location */
			gateway.upload(file);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private List<CSVFileData> buildCsvFile(Schools schools) {
		List<CSVFileData> csvdata = new ArrayList<CSVFileData>();
		for (SchoolData data : schools.getData()) {
			CSVFileData csvfileData = new CSVFileData.CSVFileDataBuilder(data.getData().getSchool_number(),
					data.getData().getName(), data.getData().getLocation().getAddress(),
					data.getData().getLocation().getAddress(), data.getData().getLocation().getAddress(),
					data.getData().getLocation().getCity(), data.getData().getLocation().getZip(),
					data.getData().getLocation().getState()).setActive("Y").setCountry_code("US").build();
			csvdata.add(csvfileData);
		}
		return csvdata;
	}

}