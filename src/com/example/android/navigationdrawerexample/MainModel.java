package com.example.android.navigationdrawerexample;

public class MainModel {
	String title;
	String desc;
	int imgRes;
	String size;
	
	public MainModel(String title, String desc, int imgRes, String size) {
		super();
		this.title = title;
		this.desc = desc;
		this.imgRes = imgRes;
		this.size = size;
	}
	public int getImgRes() {
		return imgRes;
	}
	public void setImgRes(int imgRes) {
		this.imgRes = imgRes;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
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