package com.ericsson.pc.migrationtool.bean;

public class Banner extends Model {
	
	private String id;
	private String imgFolder;
	private String imageName;
	private String isActive;
	private String home;
	private String body;
	private String url;
	private String hasGrid;	
	private String hasDetail;
	private String plans;
	private String search;
	private String accessories;
	private String cart;
	private String deals;
	
	public String getHasDetail() {
		return hasDetail;
	}
	public void setHasDetail(String hasDetail) {
		this.hasDetail = hasDetail;
	}
	public String getPlans() {
		return plans;
	}
	public void setPlans(String plans) {
		this.plans = plans;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getAccessories() {
		return accessories;
	}
	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}
	public String getCart() {
		return cart;
	}
	public void setCart(String cart) {
		this.cart = cart;
	}
	public String getDeals() {
		return deals;
	}
	public void setDeals(String deals) {
		this.deals = deals;
	}

	
	public String getHasGrid() {
		return hasGrid;
	}
	public void setHasGrid(String hasGrid) {
		this.hasGrid = hasGrid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgFolder() {
		return imgFolder;
	}
	public void setImgFolder(String imgFolder) {
		this.imgFolder = imgFolder;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
