package com.ceit.vic.platform.models;
/**
 * 前台导航条条目模型
 * @author VIC
 *
 */
public class NavItem {
	public NavItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	String href;
	String text;
	int id;
	
	public NavItem(String href, String text, int id) {
		super();
		this.href = href;
		this.text = text;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
