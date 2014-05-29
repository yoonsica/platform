package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Dep_Person;

public interface Dep_PersonDao {
	public void add(Dep_Person dep_Person);
	public void delete(int id);
	public Dep_Person getById(int id);
	public void update(Dep_Person dep_Person);
	public void deletePerson(int depId, int[] idArray);
	public List<Dep_Person> getByPersonId(int id,boolean mainDep);
}
