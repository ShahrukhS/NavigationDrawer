package com.example.android.navigationdrawerexample;

public class MainModel {
	String title;
	String desc;
	int imgRes;
	String url;
	
	public MainModel(String desc, int imgRes, String url) {
		super();
		this.desc = desc;
		this.imgRes = imgRes;
		this.url = url;
		String titleTemp[] = url.split("/");
		this.title = titleTemp[titleTemp.length-1];
	}
	public int getImgRes() {
		return imgRes;
	}
	public void setImgRes(int imgRes) {
		this.imgRes = imgRes;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}