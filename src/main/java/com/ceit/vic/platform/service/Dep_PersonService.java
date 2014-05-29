package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Dep_Person;


public interface Dep_PersonService {
	@Transactional
	public List<Dep_Person> getByPersonId(int id,boolean mainDep);
}
