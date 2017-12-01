package com.assignment.springintegration.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolData {

	@JsonProperty("data")
	private Data data;

	@JsonProperty("uri")
	private String uri;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
