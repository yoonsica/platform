package com.ceit.vic.platform.models;

public class PersonDTO {
	public PersonDTO(int id, String name, String code, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.memo = memo;
	}
	private int id;
	private String name;//真实姓名
	public PersonDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String departmentName;
	private String code;//用户名
	private String memo;
	private String sex;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	private String state;//状态，0表示正常  1-停用
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
