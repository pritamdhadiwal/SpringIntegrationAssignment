package com.assignment.springintegration.pojo;

public class Data {

	private String school_number;

	private String phone;

	private Location location;

	private String high_grade;

	private String id;

	private String state_id;

	private String last_modified;

	private Principal principal;

	private String nces_id;

	private String created;

	private String sis_id;

	private String name;

	private String low_grade;

	private String district;

	public String getSchool_number() {
		return school_number;
	}

	public void setSchool_number(String school_number) {
		this.school_number = school_number;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getHigh_grade() {
		return high_grade;
	}

	public void setHigh_grade(String high_grade) {
		this.high_grade = high_grade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}

	public Principal getPrincipal() {
		return principal;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public String getNces_id() {
		return nces_id;
	}

	public void setNces_id(String nces_id) {
		this.nces_id = nces_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getSis_id() {
		return sis_id;
	}

	public void setSis_id(String sis_id) {
		this.sis_id = sis_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLow_grade() {
		return low_grade;
	}

	public void setLow_grade(String low_grade) {
		this.low_grade = low_grade;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "ClassPojo [school_number = " + school_number + ", phone = " + phone + ", location = " + location
				+ ", high_grade = " + high_grade + ", id = " + id + ", state_id = " + state_id + ", last_modified = "
				+ last_modified + ", principal = " + principal + ", nces_id = " + nces_id + ", created = " + created
				+ ", sis_id = " + sis_id + ", name = " + name + ", low_grade = " + low_grade + ", district = "
				+ district + "]";
	}
}
