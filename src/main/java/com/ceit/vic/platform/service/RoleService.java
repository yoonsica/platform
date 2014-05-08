package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
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
	public int add(Role role);
	@Transactional
	public Role getRoleById(int id);
	/**
	 * 根据roleId获得该角色下的人员集合
	 * @param roleId
	 * @return
	 */
	@Transactional
	public List<PersonDTO> getPersonsByRoleId(int roleId,int page,int rows);
	/**
	 * 获取该角色下的人数
	 * @param roleId
	 * @return
	 */
	@Transactional
	public int getPersonsAmountByRoleId(int roleId);
	/**
	 * 取消指定人员的角色授权
	 * @param roleId
	 * @param personId
	 */
	@Transactional
	public void canclePersonRole(int roleId, String[] personId);
	
	/**
	 * 批量添加角色授权
	 * @param roleId 角色id数组
	 * @param idArray 人员id数组
	 */
	@Transactional
	public void addPersonRole(int[] roleId, int[] idArray);
	@Transactional
	public List<Role> getAllRoles();
	@Transactional
	public int getRoleIdByPersonId(int personId);
	
	@Transactional
	public void updateByPersonId(int id, int roleId);
	
	
}
