package com.assignment.springintegration.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.assignment.springintegration.constant.CSVFileConstants;
import com.assignment.springintegration.pojo.CSVTemplateData;
import com.assignment.springintegration.pojo.SchoolData;
import com.assignment.springintegration.pojo.Schools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class FileHandler {
	
	
    public File handleFile(File input) throws  IOException {
    	 String homeDir = System.getProperty("home.dir");
    	 File file = new File (homeDir, "output.csv");
		try {
			/* conversion */
			ObjectMapper objectMapper = new ObjectMapper();
			Schools schools = objectMapper.readValue(input, Schools.class);
			List<CSVTemplateData> list = populateCsv(schools);
			List<Schools> schoolList = new ArrayList<Schools>();
			schoolList.add(schools);
			String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schoolList);
			System.out.println(json);
			System.out.println("Now as CSV: ");

			// initialize and configure the mapper
			CsvMapper csvMapper = new CsvMapper();
			// we ignore unknown fields or fields not specified in schema,
			// otherwise
			// writing will fail
			// csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
			// initialize the schema
			CsvSchema schema = CsvSchema.builder().setColumnSeparator('\t')
					.addColumn(CSVFileConstants.CSV_SCHOOLCODE, CsvSchema.ColumnType.NUMBER)
					.addColumn(CSVFileConstants.CSV_SCHOOLNAME).addColumn(CSVFileConstants.CSV_ADDRESS1)
					.addColumn(CSVFileConstants.CSV_ADDRESS2).addColumn(CSVFileConstants.CSV_ADDRESS3)
					.addColumn(CSVFileConstants.CSV_CITY).addColumn(CSVFileConstants.CSV_POSTAL_CODE)
					.addColumn(CSVFileConstants.CSV_STATE_CODE).addColumn(CSVFileConstants.CSV_ACTIVE)
					.addColumn(CSVFileConstants.CSV_COUNTRY_CODE).build().withHeader();
			// schema= csvMapper.schemaFor(CSVTemplateData.class).withHeader();

			// mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path2
			// + "/"+filename+".csv.json"), data);

			// map the bean with our schema for the writer
			ObjectWriter writer = csvMapper.writerFor(CSVTemplateData.class).with(schema);
			// we write the list of objects
			writer.writeValues(file).writeAll(list);

			System.out.println(csvMapper.writer(schema).writeValueAsString(list));
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{
			
		}
	
         return file;
    }
    
    
    
    
	private List<CSVTemplateData> populateCsv(Schools schools) {
		List<CSVTemplateData> csvdata = new ArrayList<CSVTemplateData>();

		for (SchoolData data : schools.getData()) {
			CSVTemplateData csvTemplateData = new CSVTemplateData();
			csvTemplateData.setSchoolCode(data.getData().getSchool_number());
			csvTemplateData.setSchoolName(data.getData().getName());
			csvTemplateData.setAddress1(data.getData().getLocation().getAddress());
			csvTemplateData.setAddress1(data.getData().getLocation().getAddress());
			csvTemplateData.setAddress3(data.getData().getLocation().getAddress());
			csvTemplateData.setCity(data.getData().getLocation().getCity());
			csvTemplateData.setPostalCode(data.getData().getLocation().getZip());
			csvTemplateData.setStateCode(data.getData().getLocation().getState());
			csvTemplateData.setActive("Y");
			csvTemplateData.setCountryCode("US");
			csvdata.add(csvTemplateData);
		}
		return csvdata;
	}
    

}
