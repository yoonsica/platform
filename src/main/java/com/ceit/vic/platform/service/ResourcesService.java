package com.ceit.vic.platform.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.ButtonLinkDTO;
import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.RoleDTO;
import com.ceit.vic.platform.models.ZTreeNode;

public interface ResourcesService {
	/**
	 * 获得部门树的list
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<NavItem> getNavItems(Person person);
	/**
	 * 根据id获得其子节点构成的树
	 * @param parentId
	 * @param containId 节点名称中是否包含节点Id
	 * @param containDisable 是否包含未启用的模块
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getResourcesTreeById(int id,boolean containId,boolean containDisable,Person person) throws Exception;
	/**
	 * 获得资源列表
	 * @param id
	 * @param containBtnLink 是否包含页面内的按钮和链接元素
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getAllResources(boolean containBtnLink) throws Exception;
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
	@Transactional
	public void remove(int[] idArray);
	/**
	 * 获得已授权的部门列表
	 * @param resId
	 * @param page
	 * @param rows
	 * @return
	 */
	@Transactional
	public List<DepDTO> getDepAuthList(int resId, int page, int rows);
	@Transactional
	public List<ButtonLinkDTO> getButtonLinkList(int resId, int page, int rows);
	@Transactional
	public int getButtonLinkListSize(int resId);
	/**
	 * 把模块授权给多个部门
	 * @param idArray 部门id集合
	 * @param resId 模块id
	 */
	@Transactional
	public void addResAccess(int[] idArray, int resId, int accessType);
	@Transactional
	public void deleteResAccess(int[] idArray, int resId, int accessType);
	@Transactional
	public int getAuthTotal(int resId,int accessType);
	@Transactional
	public List<RoleDTO> getRoleAuthList(int resId, int page, int rows);
	@Transactional
	public List<PersonDTO> getPersonAuthList(int resId, int page, int rows);
	@Transactional
	public List<Resources> getByParamMap(Map<String, Object> map);
	@Transactional
	public Resources getResourcesById(int parentId);
	@Transactional
	public List<Resources> getByLink(String link);
	@Transactional
	List<Resources> getResources(int accessId, int accessType, int pageIndex, int pageSize);
	@Transactional
	int getResourceCount(int accessId, int accessType);
	/**
	 * 判断person对于id为resid的资源是否有授权
	 * @param person
	 * @param resId
	 * @return
	 */
	@Transactional
	public boolean isAuthOrNot(Person person,int resId);
}
