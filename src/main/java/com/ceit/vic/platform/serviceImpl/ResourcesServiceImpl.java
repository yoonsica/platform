package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.ResAccessDao;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.models.ButtonLinkDTO;
import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.ResAccess;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.ResourcesService;

@Service
public class ResourcesServiceImpl implements ResourcesService {
	@Autowired
	ResourcesDao resourcesDao;
	@Autowired
	IDPROVIDERDao idproviderDao;
	@Autowired
	ResAccessDao resAccessDao;
	@Autowired
	DepartmentDao departmentDao;

	@Override
	public List<NavItem> getNavItems() {
		List<Resources> rsList = resourcesDao.getNavResources();
		List<NavItem> navItems = new ArrayList<NavItem>();
		for (Resources resource : rsList) {
			NavItem item = new NavItem(resource.getLink(), resource.getName(),
					resource.getId());
			navItems.add(item);
		}

		return navItems;
	}

	@Override
	public List<ZTreeNode> getResourcesTreeById(int id, boolean containId,
			boolean containDisable) throws Exception {
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		List<Object[]> rList = resourcesDao.getResourcesTreeById(id,
				containDisable);
		for (Object[] objects : rList) {
			ZTreeNode node = new ZTreeNode();
			node.setId(Integer.parseInt(objects[0].toString()));
			if (containId) {
				node.setName("(" + node.getId() + ")" + objects[8].toString());
			} else {
				node.setName(objects[8].toString());
			}
			node.setpId(Integer.parseInt(objects[9].toString()));
			if (null != objects[3]) {
				node.setIcon("static/easyui/themes/icons/"
						+ objects[3].toString());
				node.setIconCls(objects[3].toString().split("\\.")[0]);
			}
			if (objects[6].toString().equals("1")
					|| objects[6].toString().equals("0")) {// 1-目录
				node.setIsParent("true");
			} else if (objects[6].toString().equals("2")) {
				node.setHref(objects[4].toString());// 链接
			}

			// node.setIcon(objects[3].toString());
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public ModuleInfoDTO getModuleInfoById(int moduleId) {
		Resources resources = resourcesDao.getResourceById(moduleId);
		// 组织dto
		ModuleInfoDTO dto = new ModuleInfoDTO();
		dto.setId(resources.getId());
		dto.setName(resources.getName());
		dto.setLink(resources.getLink() == null ? "" : resources.getLink());
		dto.setIcon(resources.getIcon());
		if (resources.getId() == 1) {
			dto.setParent("");
		} else {
			/*
			 * Resources parentResources =
			 * resourcesDao.getResourceById(resources.getParentId());
			 * dto.setParent(parentResources.getName());
			 */
			dto.setParent(String.valueOf(resources.getParentId()));
			Resources parentRs = resourcesDao.getResourceById(resources
					.getParentId());
			dto.setParentName("(" + parentRs.getId() + ")" + parentRs.getName());
		}
		dto.setState(resources.getState().equals("1") ? "启用" : "停用");
		// dto.setState(resources.getState());
		return dto;
	}

	@Override
	public void updateResources(ModuleInfoDTO moduleInfo) {
		Resources resources = resourcesDao.getResourceById(moduleInfo.getId());
		resources.setName(moduleInfo.getName());
		resources.setLink(moduleInfo.getLink());
		resources.setIcon(moduleInfo.getIcon());
		resources.setState(moduleInfo.getState());
		if (!moduleInfo.getParent().equals("")) {
			resources.setParentId(Integer.valueOf(moduleInfo.getParent()));
		}
		// dao中更新
		resourcesDao.update(resources);
	}

	@Override
	public void up(int moduleId) {
		Resources module1 = resourcesDao.getResourceById(moduleId);
		Resources module2 = resourcesDao.getResourceToDown(
				module1.getParentId(), module1.getDispIndex());
		int tmp = module2.getDispIndex();
		module2.setDispIndex(module1.getDispIndex());
		module1.setDispIndex(tmp);
		resourcesDao.update(module1);
		resourcesDao.update(module2);
	}

	@Override
	public void down(int moduleId) {
		Resources module1 = resourcesDao.getResourceById(Integer
				.valueOf(moduleId));
		Resources module2 = resourcesDao.getResourceToUp(module1.getParentId(),
				module1.getDispIndex());
		int tmp = module2.getDispIndex();
		module2.setDispIndex(module1.getDispIndex());
		module1.setDispIndex(tmp);
		resourcesDao.update(module1);
		resourcesDao.update(module2);
	}

	@Override
	public int addResource(ModuleInfoDTO moduleInfo, boolean isFolder) {
		int id = idproviderDao.getCurrentId("RESOURCES");
		Resources resources = new Resources();
		resources.setId(id);
		resources.setDispIndex(id);
		resources.setIcon(moduleInfo.getIcon());
		resources.setName(moduleInfo.getName());
		resources.setParentId(Integer.valueOf(moduleInfo.getParent()));
		resources.setType(moduleInfo.getType() == null ? "0" : moduleInfo
				.getType());
		resources.setState(1 + "");
		if (resources.getType().equals("1")) {
			resources.setIcon("169.gif");
		}else if (resources.getType().equals("2")) {
			resources.setIcon("globe.gif");
		}
		if (isFolder) {
			resources.setmType(1 + "");
		} else {
			resources.setmType(2 + "");
			resources.setLink(moduleInfo.getLink());
			resources.setState(moduleInfo.getState());
		}

		resourcesDao.add(resources);
		idproviderDao.add("RESOURCES");
		return resources.getId();
	}

	@Override
	public void remove(int moduleId) {
		resourcesDao.remove(moduleId);
	}

	@Override
	public List<DepDTO> getDepAuthList(int resId, int page, int rows) {
		int firstResult, maxResults;
		firstResult = (page - 1) * rows;
		maxResults = rows;
		// 获得已授权的部门id集合
		List<Integer> idList = resAccessDao.getAccessIdsByResId(resId, 2,
				firstResult, maxResults);
		List<Department> depList = departmentDao.getDepartmentsByIds(idList);
		List<DepDTO> dtoList = new ArrayList<DepDTO>();
		for (Department department : depList) {
			DepDTO dto = new DepDTO(department.getId(), department.getName(),
					department.getMemo());
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<ButtonLinkDTO> getButtonLinkList(int resId, int page, int rows) {
		int firstResult, maxResults;
		firstResult = (page - 1) * rows;
		maxResults = rows;
		List<Resources> list = resourcesDao.getButtonLinkByResId(resId,
				firstResult, maxResults);
		List<ButtonLinkDTO> dtoList = new ArrayList<ButtonLinkDTO>();
		for (Resources resources : list) {
			ButtonLinkDTO dto = new ButtonLinkDTO(resources.getId(),
					resources.getName(), resources.getLink(),
					resources.getMemo());
			if (resources.getType().equals("1")) {
				dto.setType("按钮");
			}else if (resources.getType().equals("2")) {
				dto.setType("链接");
			}
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public int getButtonLinkListSize(int resId) {

		return resourcesDao.getButtonLinkAmount(resId);
	}

	@Override
	public void remove(int[] idArray) {
		resourcesDao.remove(idArray);
		
	}

	@Override
	public List<ZTreeNode> getAllResources(boolean containBtnLink)
			throws Exception {
		List<Resources> resList = resourcesDao.getAllResources(containBtnLink);
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (Resources resources : resList) {
			ZTreeNode node = new ZTreeNode(resources.getName(),resources.getId(),resources.getParentId());
			node.setIcon("static/easyui/themes/icons/"+resources.getIcon());
			node.setIconCls(resources.getIcon().split("\\.")[0]);
			if (resources.getType().equals("0")) {
				if (resources.getmType().equals("1")
						|| resources.getmType().equals("0")) {// 1-目录
					node.setIsParent("true");
					node.setHref(null==resources.getLink()?"":resources.getLink());
				} 
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public void addResAccess(int[] idArray, int resId, int accessType) {
		for (int i : idArray) {
			int id = idproviderDao.getCurrentId("RESACCESS");
			ResAccess resAccess = new ResAccess();
			resAccess.setId(id);
			resAccess.setAccessId(i);//部门、人员、角色id
			resAccess.setAccessType(accessType);
			resAccess.setResId(resId);
			resAccessDao.add(resAccess);
			idproviderDao.add("RESACCESS");
		}		
	}

	@Override
	public void deleteResAccess(int[] idArray, int resId, int accessType) {
		resAccessDao.deleteResAccess(idArray,resId,accessType);
		
	}

}
