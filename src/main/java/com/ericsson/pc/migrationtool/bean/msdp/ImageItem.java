package com.ericsson.pc.migrationtool.bean.msdp;

import java.util.ArrayList;
import java.util.List;

public class ImageItem {
	
	private String name;
	
	private String path;

	private boolean views;
	
	public boolean isViews() {
		return views;
	}

	public void setViews(boolean views) {
		this.views = views;
	}

	private List<ImageItem> thumbs = new ArrayList<ImageItem>();
	public List<ImageItem> getThumbs() {
		return thumbs;
	}

	public void setThumbs(List<ImageItem> thumbs) {
		this.thumbs = thumbs;
	}

	public List<ImageItem> getFull() {
		return full;
	}

	public void setFull(List<ImageItem> full) {
		this.full = full;
	}

	private List<ImageItem> full = new ArrayList<ImageItem>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
