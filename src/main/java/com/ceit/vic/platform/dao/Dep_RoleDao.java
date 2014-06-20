package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Dep_Role;

public interface Dep_RoleDao {
	/**
	 * 通过角色id获取该角色下的部门id集合
	 * @param roleId
	 */
	public List<Integer> getDepIdsByRoleId(int roleId,int firstResult,int maxResults);

	public int getDepAmountByRoleId(int roleId);

	public void removeByRoleIdDepId(int roleId, String[] depId);
	
	public List<Dep_Role> getDepRole(int roleId, int depId);

	public void add(Dep_Role dep_Role);

	public List<Dep_Role> getByDepId(int depId);

	public void deleteByDepIds(int[] idArray);

	public void removeUnusedRoles(int[] roleIds, int depId);
}
