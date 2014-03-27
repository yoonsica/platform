package com.ceit.vic.platform.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.ZTreeNode;

public interface ResourcesService {
	/**
	 * 获得部门树的list
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<NavItem> getNavItems();
	/**
	 * 根据id获得其子节点构成的树
	 * @param parentId
	 * @param containId 节点名称中是否包含节点Id
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getResourcesTreeById(int id,boolean containId) throws Exception;
	@Transactional(readOnly=true)
	public ModuleInfoDTO getModuleInfoById(int moduleId);
	
	@Transactional
	public void updateResources(ModuleInfoDTO moduleInfo);
}
