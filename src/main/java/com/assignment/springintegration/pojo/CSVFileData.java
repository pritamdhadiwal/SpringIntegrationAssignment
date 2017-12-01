package com.assignment.springintegration.pojo;


public class CSVFileData {

	private CSVFileData() {
	}

	public static CSVFileDataBuilder builder() {
		return new CSVFileDataBuilder();
	}

	private String schoolCode;
	private String schoolName;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String postalCode;
	private String stateCode;
	private String active;
	private String countryCode;

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {

		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public static class CSVFileDataBuilder {

		private String schoolCode;
		private String schoolName;
		private String address1;
		private String address2;
		private String address3;
		private String city;
		private String postalCode;
		private String stateCode;

		// optional parameters
		private String active;
		private String countrycode;
		private CSVFileData csvFileData;

		public CSVFileDataBuilder() {
			csvFileData = new CSVFileData();
		}

		public CSVFileData build() {
			return csvFileData;
		}

		public CSVFileDataBuilder setSchoolCode(final String schoolCode) {
			csvFileData.setSchoolCode(schoolCode);
			return this;
		}

		public CSVFileDataBuilder setSchoolName(final String schoolName) {
			csvFileData.setSchoolName(schoolName);
			return this;
		}

		public CSVFileDataBuilder setAddress1(final String address1) {
			csvFileData.setAddress1(address1);
			return this;
		}

		public CSVFileDataBuilder setAddress2(final String address2) {
			csvFileData.setAddress1(address2);
			return this;
		}

		public CSVFileDataBuilder setAddress3(final String address3) {
			csvFileData.setAddress1(address3);
			return this;
		}

		public CSVFileDataBuilder setPostalCode(final String postalCode) {
			csvFileData.setPostalCode(postalCode);
			return this;
		}

		public CSVFileDataBuilder setCity(final String city) {
			csvFileData.setCity(city);
			return this;
		}

		public CSVFileDataBuilder setStateCode(final String stateCode) {
			csvFileData.setStateCode(stateCode);
			return this;
		}

		public CSVFileDataBuilder setActive(final String active) {
			csvFileData.setActive(active);
			return this;
		}

		public CSVFileDataBuilder setCountrycode(final String countrycode) {
			csvFileData.setActive(countrycode);
			return this;
		}

	}

}
