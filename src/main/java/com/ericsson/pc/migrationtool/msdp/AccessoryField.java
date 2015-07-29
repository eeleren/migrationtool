package com.ericsson.pc.migrationtool.msdp;

public class AccessoryField implements Comparable<AccessoryField>  {

	private int order;
	private boolean mandatory;
	private String name;
	private Object value;
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
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int compareTo(AccessoryField o) {
		// TODO Auto-generated method stub
		return getOrder() - o.getOrder();
	}
}




	 
