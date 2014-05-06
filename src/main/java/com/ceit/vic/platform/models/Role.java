package com.ceit.vic.platform.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Role {
	@Id
	private int id;
	private int parentId;
	private String name;
	private String memo;
	private int dispIndex;
	private int isFolder;//0-角色，1--目录
	
	public int getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(int isFolder) {
		this.isFolder = isFolder;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
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
	public int getDispIndex() {
		return dispIndex;
	}
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}
	
	
}
