package com.ceit.vic.platform.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class LogType {

	@Id
	private int id;
	private String name;
	
	public LogType() {
	}

	public LogType(int id) {
		this.id = id;
	}

	public LogType(int id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "LogType [id=" + id + ", name=" + name + "]";
	}
}
