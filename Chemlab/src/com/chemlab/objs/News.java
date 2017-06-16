package com.chemlab.objs;

import java.io.Serializable;

public class News implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public News(){
		
	}

	private String ArticleID;
	private String Title;
	private String Content_1;
	private String DateTime;
	private String Hits;
	private String Member_Name;
	private String type;
	private String infor;
	
	public String getArticleID() {
		return ArticleID;
	}

	public void setArticleID(String articleID) {
		ArticleID = articleID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getContent_1() {
		return Content_1;
	}

	public void setContent_1(String content_1) {
		Content_1 = content_1;
	}

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getMember_Name() {
		return Member_Name;
	}

	public void setMember_Name(String member_Name) {
		Member_Name = member_Name;
	}

	public String getHits() {
		return Hits;
	}

	public void setHits(String hits) {
		Hits = hits;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}
}
