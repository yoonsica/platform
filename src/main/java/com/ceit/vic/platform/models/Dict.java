package com.ceit.vic.platform.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Dict {
	@Id
	private int id;
	private String name;
	private String value;//内部值（如xg）
	private int dispIndex;
	private int parentId;
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
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
}
