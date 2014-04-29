package com.ceit.vic.platform.dao;

import java.util.List;

public interface Person_RoleDao {
	/**
	 * 通过角色id获取该角色下的人员id集合
	 * @param roleId
	 */
	public List<Integer> getPersonIdsByRoleId(int roleId,int firstResult,int maxResults);

	public int getPersonAmountByRoleId(int roleId);
}
