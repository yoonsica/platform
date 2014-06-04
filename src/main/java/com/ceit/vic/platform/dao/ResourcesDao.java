package com.ceit.vic.platform.dao;

import java.util.List;
import java.util.Map;

import com.ceit.vic.platform.models.Resources;

public interface ResourcesDao {
	public List<Resources> getByParamMap(Map<String, Object> paraMap);
	/**
	 * 获得导航栏菜单项
	 * @return
	 */
	public List<Resources> getNavResources();
	/**
	 * 根据id获得id=id的节点及其所有子项目
	 * @param parentId
	 * @param containDisable 是否包含停用模块
	 * @return
	 */
	public List<Object[]> getResourcesTreeById(int id,boolean containDisable);
	public List<Resources> getAllResources(boolean containDisable);
	/**
	 * 根据id获得resources对象
	 * @param id
	 * @return
	 */
	public Resources getResourceById(int id);
	public void update(Resources resources);
	/**
	 * 找到相同父节点下比显示顺序比dispIndex高的最近节点
	 * @param parentId
	 * @param dispIndex
	 * @return
	 */
	public Resources getResourceToDown(int parentId, int dispIndex);
	/**
	 *  找到相同父节点下比显示顺序比dispIndex低的最近节点
	 * @param parentId
	 * @param dispIndex
	 * @return
	 */
	public Resources getResourceToUp(int parentId, int dispIndex);
	
	/**
	 * 添加资源
	 * @param resources
	 */
	public void add(Resources resources);
	public void remove(int moduleId);
	public List<Resources> getButtonLinkByResId(int resId, int firstResult,
			int maxResults);
	public int getButtonLinkAmount(int resId);
	public void remove(int[] idArray);
	public List<Resources> getResourceByLink(String link);
	
	List<Resources> getResourcesByAccessId(int accessId, int accessType, int pageIndex, int pageSize);
	int getResourcesCountByAccessId(int accessId, int accessType);
}
