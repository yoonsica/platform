package com.ceit.vic.platform.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Dep_Person {
	@Id
	private int id;
	private int depId;
	private int personId;
	private int mainDep;//是否是主部门  0--兼职部门    1--主部门
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public int getMainDep() {
		return mainDep;
	}
	public void setMainDep(int mainDep) {
		this.mainDep = mainDep;
	}
	
	
}
