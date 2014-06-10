package com.ceit.vic.platform.models;

import javax.persistence.Entity;

public class SearchResultDTO {
	private int id;
	private String result;
	private int typeId;
	public SearchResultDTO(int id, String result, int typeId) {
		this.id = id;
		this.result = result;
		this.typeId = typeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}
