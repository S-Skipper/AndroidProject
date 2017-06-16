package com.chemlab.objs;

public class Equipment {

	/*
	 * {"equip_name":"12", "model":"1", "factory":"b", "detail":"c",
	 * "price":"10000"}
	 */

	private String name;
	private String model;
	private String price;
	private String factory;
	private String detail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
