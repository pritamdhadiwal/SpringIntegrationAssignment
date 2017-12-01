package com.assignment.springintegration.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schools  {
	
	@JsonProperty("data")
	private SchoolData[] data;

	@JsonProperty("links")
	private Links[] links;

	@JsonProperty("paging")
	private Paging paging;

	public SchoolData[] getData() {
		return data;
	}

	public void setData(SchoolData[] data) {
		this.data = data;
	}

	public Links[] getLinks() {
		return links;
	}

	public void setLinks(Links[] links) {
		this.links = links;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "ClassPojo [data = " + data + ", links = " + links + ", paging = " + paging + "]";
	}}
