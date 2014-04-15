package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.PersonDTO;

public interface PersonService {
	/**
	 * 获得部门树的list
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<PersonDTO> getPersonsByDepId(int depId);
}
