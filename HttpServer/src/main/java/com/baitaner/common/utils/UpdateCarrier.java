package com.baitaner.common.utils;

public class UpdateCarrier {

	private String field;
	
	private String logical;
	
	private Object value;
	
	public UpdateCarrier(String field, String logical, Object value) {
		this.field = field;
		this.logical = logical;
		this.value = value;
	}

	public String getExpression() {
		return field + " " + logical;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getLogical() {
		return logical;
	}

	public void setLogical(String logical) {
		this.logical = logical;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
