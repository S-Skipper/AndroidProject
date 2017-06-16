package com.chemlab.objs;

public class WorkInfo {

	//[{"time_id":"68","ID":"20170103","name":"董老师","date_time":"2017-5-24 11:54:07","state":"已签到"},

	private String time_id;
	private String ID;
	private String name;
	private String date_time;
	private String state;
	
	public WorkInfo() {
	}

	public String getTime_id() {
		return time_id;
	}

	public void setTime_id(String time_id) {
		this.time_id = time_id;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
