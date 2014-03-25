package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Resources;

public interface ResourcesDao {
	/**
	 * 获得导航栏菜单项
	 * @return
	 */
	public List<Resources> getNavResources();
	/**
	 * 根据id获得id=id的节点及其所有子项目
	 * @param parentId
	 * @return
	 */
	public List<Object[]> getResourcesTreeById(int id);
	/**
	 * 根据id获得resources对象
	 * @param id
	 * @return
	 */
	public Resources getResourceById(int id);
}
