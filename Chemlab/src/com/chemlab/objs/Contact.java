package com.chemlab.objs;

import java.io.Serializable;

public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private int imageId;
	private String name;
	private String idebtity;
	private String phoneNumber;
	private String phoneShort;
	private String weChatNumber;
	private String qqNumber;
	private String email;
	private String address;
	private String create_time;
	private String last_time;
	
	public Contact() {
	}
	
	public Contact(int imageId) {
		this.imageId = imageId;
	}
	
	public void setAttribute(String key,String value){
		if ("ID".equals(key)) {
			setId(value);
		}else if ("name".equals(key)) {
			setName(value);
		}else if ("creat_time".equals(key)) {
			setCreate_time(value);
		}else if ("idebtity".equals(key)) {
			setIdebtity(value);
		}else if ("phonenumber_long".equals(key)) {
			setPhoneNumber(value);
		}else if ("phonenumber_short".equals(key)) {
			setPhoneShort(value);
		}else if ("QQ".equals(key)) {
			setQQNumber(value);
		}else if ("email".equals(key)) {
			setEmail(value);
		}else if ("address".equals(key)) {
			setAddress(value);
		}else if ("last_time".equals(key)) {
			setLast_time(value);
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLast_time() {
		return last_time;
	}

	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}

	public String getWeChatNumber() {
		return weChatNumber;
	}

	public void setWeChatNumber(String weChatNumber) {
		this.weChatNumber = weChatNumber;
	}

	public String getIdebtity() {
		return idebtity;
	}

	public void setIdebtity(String idebtity) {
		this.idebtity = idebtity;
	}

	public String getPhoneShort() {
		return phoneShort;
	}

	public void setPhoneShort(String phoneShort) {
		this.phoneShort = phoneShort;
	}
}
