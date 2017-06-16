package com.dtt.chemlab.objs;

import java.io.Serializable;

public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int imageId;
	private String name;
	private String phoneNumber;
	private String qqNumber;
	private String email;
	
	public Contact(int imageId,String name,String phoneNumber) {
		this.imageId = imageId;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setQQNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getQQNumber() {
		return qqNumber;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}
