package com.ceit.vic.platform.models;

import java.util.List;

public class PersonListDTO {
	private int total;
	private List<PersonDTO> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<PersonDTO> getRows() {
		return rows;
	}
	public void setRows(List<PersonDTO> rows) {
		this.rows = rows;
	}
	
}
