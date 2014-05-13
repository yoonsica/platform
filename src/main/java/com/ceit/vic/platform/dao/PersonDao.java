package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Person;

public interface PersonDao {
	public void add(Person person);
	public void delete(int id);
	public void update(Person person);
	public List<Person> getPersonsByIds(List<Integer> personIds);
	public Person getPersonsById(int id);
	public Person getPersonToDown(int depId, int dispIndex);
	public Person getPersonToUp(int depId, int dispIndex);
	public void multDelete(int[] idArray);
	public void resetPassword(int[] idArray);
}
