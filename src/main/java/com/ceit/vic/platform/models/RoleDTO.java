package com.ceit.vic.platform.models;

public class RoleDTO {
	private int id;
	private String name;
	public RoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String memo;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public RoleDTO(int id, String name, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.memo = memo;
	}
	
}