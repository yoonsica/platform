package com.ceit.vic.platform.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ResAccess {
	@Id
	private int id;
	private int resId;
	private int accessType;//访问类，0-角色，1-人员，2-部门
	private int accessId;//访客ID
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public int getAccessType() {
		return accessType;
	}
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
	public int getAccessId() {
		return accessId;
	}
	public void setAccessId(int accessId) {
		this.accessId = accessId;
	}
	
	
}
