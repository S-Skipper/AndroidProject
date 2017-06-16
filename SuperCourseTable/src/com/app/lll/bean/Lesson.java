package com.app.lll.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lesson  implements Serializable{
	private long id;				//课程id
	private String name;
	private int num;				//第几课
	private String lessonDate;		//上课日期
	private String sTime;			//课程开始时间
	private String eTime;			//课程结束时间
	private int status;				//0 未上 1已上2 系统确认
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getLessonDate() {
		return lessonDate;
	}

	public void setLessonDate(String lessonDate) {
		this.lessonDate = lessonDate;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	public String getLessonDateSTime(){
		return new StringBuffer().append(lessonDate).append(" ").append(this.sTime).toString();
	}
	
}
