package com.dtt.dmusic;

public class Music {

	private String name;
	private int order;
	
	private int imageId = R.drawable.music_icon;
	
	public Music(String musicName, int orderNumber) {
		name = musicName;
		order = orderNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public int getOrder() {
		return order;
	}
	
	public int getImageId() {
		return imageId;
	}
}
