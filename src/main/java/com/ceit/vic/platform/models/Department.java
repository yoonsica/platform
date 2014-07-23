package com.ceit.vic.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4038861813080406428L;
	@Id
	private int id;
	private String name;//部门名称
	private int parentId;//上级部门id
	private int dispindex;//显示索引
	private String memo;//备注
	private String removable="true";//是否可以删除
	public String getRemovable() {
		return removable;
	}
	public void setRemovable(String removable) {
		this.removable = removable;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getDispindex() {
		return dispindex;
	}
	public void setDispindex(int dispindex) {
		this.dispindex = dispindex;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
