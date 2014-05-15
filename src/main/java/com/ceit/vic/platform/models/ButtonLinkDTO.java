package com.ceit.vic.platform.models;

public class ButtonLinkDTO {
	public ButtonLinkDTO(int id, String name, String link, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.link = link;
		this.memo = memo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ButtonLinkDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	int id;
	String name;
	String link;
	String memo;
	String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
