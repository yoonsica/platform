package com.ceit.vic.platform.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
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
	 * @param containDisable 是否包含未启用的模块
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getResourcesTreeById(int id,boolean containId,boolean containDisable) throws Exception;
	@Transactional(readOnly=true)
	public ModuleInfoDTO getModuleInfoById(int moduleId);
	
	@Transactional
	public void updateResources(ModuleInfoDTO moduleInfo);
	/**
	 * 调整节点显示顺序
	 * @param moduleId 要调整的节点id
	 */
	@Transactional
	public void up(int moduleId);
	@Transactional
	public void down(int moduleId);
	/**
	 * 添加资源
	 * @param moduleInfo
	 * @param isFolder 是否是目录
	 * @return 返回添加的资源id
	 */
	@Transactional
	public int addResource(ModuleInfoDTO moduleInfo, boolean isFolder);
	@Transactional
	public void remove(int moduleId);
	/**
	 * 获得已授权的部门列表
	 * @param resId
	 * @param page
	 * @param rows
	 * @return
	 */
	@Transactional
	public List<DepDTO> getDepAuthList(int resId, int page, int rows);
}
