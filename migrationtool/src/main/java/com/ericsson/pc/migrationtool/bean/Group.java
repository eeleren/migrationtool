package com.ericsson.pc.migrationtool.bean;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String value;
	private String id;
	private String order;
	private String thumb;
	private List<Spec> listSpec = new ArrayList<Spec>();
	
	public class Spec {
		private String type;
		private String value;
		private String order;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public List<Spec> getListSpec() {
		return listSpec;
	}

	public void setListSpec(List<Spec> listSpec) {
		this.listSpec = listSpec;
	}
	
	

}
