package com.ericsson.pc.migrationtool.bean;

public class PhoneField implements Comparable<PhoneField> {
	 
	private int order;
	private boolean mandatory;
	private String name;
	private String value;
	private String defaultValue;
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(PhoneField o) {
		// TODO Auto-generated method stub
		return getOrder() - o.getOrder();
	}

}
