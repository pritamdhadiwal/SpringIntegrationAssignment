package com.assignment.springintegration.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("rel")
	private String rel;

	@JsonProperty("uri")
	private String uri;

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "ClassPojo [rel = " + rel + ", uri = " + uri + "]";
	}

}
