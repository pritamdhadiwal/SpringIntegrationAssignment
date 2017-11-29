package com.assignment.springintegration.pojo;

public class CSVFileData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public CSVFileData(CSVFileDataBuilder csvFileDataBuilder) {
		this.schoolCode = csvFileDataBuilder.SCHOOLCODE;
		this.schoolName = csvFileDataBuilder.SCHOOLNAME;
		this.address1 = csvFileDataBuilder.ADDRESS1;
		this.address2 = csvFileDataBuilder.ADDRESS2;
		this.address3 = csvFileDataBuilder.ADDRESS3;
		this.city = csvFileDataBuilder.CITY;
		this.postalCode = csvFileDataBuilder.POSTAL_CODE;
		this.stateCode = csvFileDataBuilder.STATE_CODE;
	}

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

	// Builder Class
	public static class CSVFileDataBuilder {
		// required parameters
		private String SCHOOLCODE;
		private String SCHOOLNAME;
		private String ADDRESS1;
		private String ADDRESS2;
		private String ADDRESS3;
		private String CITY;
		private String POSTAL_CODE;
		private String STATE_CODE;

		// optional parameters
		private String active;
		private String country_code;

		public CSVFileDataBuilder setActive(String active) {
			this.active = "Y";
			return this;
		}

		public CSVFileDataBuilder setCountry_code(String country_code) {
			this.country_code = "US";
			return this;
		}

		public CSVFileDataBuilder(String schoolCode, String schoolName, String address1, String address2,
				String address3, String city, String postalCode, String stateCode) {
			this.SCHOOLCODE = schoolCode;
			this.SCHOOLNAME = schoolName;
			this.ADDRESS1 = address1;
			this.ADDRESS2 = address2;
			this.ADDRESS3 = address3;
			this.CITY = city;
			this.POSTAL_CODE = postalCode;
			this.STATE_CODE = stateCode;
		}

		public CSVFileData build() {
			return new CSVFileData(this);
		}
	}
}
