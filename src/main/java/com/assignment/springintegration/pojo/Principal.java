package com.assignment.springintegration.pojo;

public class Principal {

	private String email;

	private String name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ClassPojo [email = " + email + ", name = " + name + "]";
	}
}
