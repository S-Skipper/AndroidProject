package com.xiaowu.banner.demo;

/**
 * ���ʵ��
 * 
 * @author wwj_748
 * 
 */
public class AdDomain {
	private String id; // ���id
	private String date; // ����
	private String title; // ����
	private String topicFrom; //ѡ������ 
	private String topic; // ѡ��
	private String imgUrl; // ͼƬurl
	private boolean isAd; // �Ƿ�Ϊ���
	private String startTime; // ��濪ʼʱ��
	private String endTime; // ������ʱ��
	private String targetUrl; // Ŀ��url
	private int width; // ��
	private int height; // ��
	private boolean available; // �Ƿ����
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public boolean isAd() {
		return isAd;
	}

	public void setAd(boolean isAd) {
		this.isAd = isAd;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicFrom() {
		return topicFrom;
	}

	public void setTopicFrom(String topicFrom) {
		this.topicFrom = topicFrom;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	

}
