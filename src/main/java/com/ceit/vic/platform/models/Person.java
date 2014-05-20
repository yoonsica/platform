package com.ceit.vic.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3568477748519053378L;
	@Id
	private int id;
	private String name;//真实姓名
	private String password;
	private String code;//用户名
	private int dispIndex;//显示顺序
	private String memo;
	private String state;//状态，0表示正常  1-停用
	private String sex;
	
	public Person() {
	}

	public Person(int id) {
		this.id = id;
	}
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getDispIndex() {
		return dispIndex;
	}
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
