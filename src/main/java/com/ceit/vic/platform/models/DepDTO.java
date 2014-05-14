package com.ceit.vic.platform.models;

public class DepDTO {
	private int id;
	private String name;
	private String memo;
	public int getId() {
		return id;
	}
	public DepDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public DepDTO(int id, String name, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.memo = memo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
