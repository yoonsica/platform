package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.ZTreeNode;

public interface RoleService {
	/**
	 * 获得角色树，虽然不知道为啥要求要用树
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getRoleTree();
	@Transactional
	public void update(Role role);
	@Transactional
	public void delete(int id);
	@Transactional
	public void add(Role role);
	
	public Role getRoleById(int id);
	/**
	 * 根据roleId获得该角色下的人员集合
	 * @param roleId
	 * @return
	 */
	public List<Person> getPersonsByRoleId(int roleId);
	
	
}
