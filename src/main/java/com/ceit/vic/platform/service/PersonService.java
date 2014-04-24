package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.PersonDTO;

public interface PersonService {
	/**
	 * 根据部门id获得人员的list
	 * @param page 当前的页数
	 * @param rows 每页的条数
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<PersonDTO> getPersonsByDepId(int depId,int page,int rows);
	
	@Transactional(readOnly=true)
	public int getTotalPersonsByDepId(int depId);
}
