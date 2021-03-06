package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Person;

public interface DepPersonDao {
	public List<Person> getPersonsByDepId(int depId,int firstResult,int maxResults);
	/**
	 * 根据部门id获得该部门下总人数
	 * @param depId
	 * @return
	 */
	public int getTotalPersonByDepId(int depId);
	public List<Dep_Person> getByPersonId(int personId);
	public Dep_Person getMainByPersonId(int personId);
}
