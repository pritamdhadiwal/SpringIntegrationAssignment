package com.assignment.springintegration.pojo;

public class Paging {

	private String total;

	private String count;

	private String current;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	@Override
	public String toString() {
		return "ClassPojo [total = " + total + ", count = " + count + ", current = " + current + "]";
	}
}
