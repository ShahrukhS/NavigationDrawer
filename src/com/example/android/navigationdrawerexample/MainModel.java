package com.example.android.navigationdrawerexample;

public class MainModel {
	String title;
	String desc;
	int imgRes;
	double size;
	
	public MainModel(String title, String desc, int imgRes, double size) {
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
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
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