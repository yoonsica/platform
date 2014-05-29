package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Person_Role;

public interface Person_RoleDao {
	/**
	 * 通过角色id获取该角色下的人员id集合
	 * @param roleId
	 */
	public List<Integer> getPersonIdsByRoleId(int roleId,int firstResult,int maxResults);

	public int getPersonAmountByRoleId(int roleId);

	public void removeByRoleIdPersonId(int roleId, String[] personId);
	
	/**
	 * 通过角色id和personId获得personrole对象集合
	 * @param roleId
	 * @param personId
	 * @return
	 */
	public List<Person_Role> getPersonRole(int roleId, int personId);

	public void add(Person_Role person_Role);

	public List<Person_Role> getPersonRoleByPersonId(int personId);

	public void deleteByPersonIds(int[] idArray);

	public void removeUnusedRoles(int[] roleIds, int personId);
}
