package com.ceit.vic.platform.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Dictionary {
	@Id
	private int id;
	private String name;
	private String value;//内部值（如xg）
	private int dispIndex;
	@Column(name="PARENT_ID")
	private int parentId;
	@Column(name="IS_LEAF")
	private int isLeaf;//分类，0-大类（字典分类）;1-小类（字典明细） ???
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getDispIndex() {
		return dispIndex;
	}
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isLeaf() {
		return isLeaf == 1 ? true : false;
	}
	
	
}
