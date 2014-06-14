package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Person;
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
	/**
	 * 添加人员
	 * @param person
	 * @param roleId 人员角色
	 * @param depId 所属部门
	 */
	@Transactional
	public int add(Person person, int roleId, int depId);
	
	@Transactional
	public String up(int depId,int personId);
	
	@Transactional
	public String down(int depId,int personId);
	@Transactional
	public Person getPersonById(int personId);
	@Transactional
	public void update(Person person);
	@Transactional
	public void updateDepartment(int id, int depId);
	@Transactional
	public void delete(int[] idArray);
	@Transactional
	public void deleteDepPerson(int depId, int[] idArray);
	@Transactional
	public void deletePersonRole(int[] idArray);
	@Transactional
	public List<Person> getPersonsByIds(int[] idArray);
	@Transactional
	public void resetPassword(int[] idArray);
	
	/**
	 * 通过用户名查找用户对象
	 * @param code
	 * @return
	 */
	@Transactional
	public List<Person> getPersonsByCodeName(String code);
	@Transactional
	public boolean changePassword(String oldPasswordMd5, String newPassword,Person person);
}
