package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Person;

public interface DepPersonDao {
	public List<Person> getPersonsByDepId(int depId);
}
