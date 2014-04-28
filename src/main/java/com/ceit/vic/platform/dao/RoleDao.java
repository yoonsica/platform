package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Role;

public interface RoleDao {
	/**
	 * 根据id获得id=id的节点及其所有子项目
	 * @param parentId
	 * @return
	 */
	public List<Role> getRolesTreeById(int id);
	/**
	 * 根据id获得Role对象
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);
	/**
	 * 添加角色
	 * @param role
	 */
	public void add(Role role);
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void remove(int roleId);
	
	public void update(Role role);
}
