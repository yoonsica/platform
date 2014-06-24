package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepPersonDao;
import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.PersonDao;
import com.ceit.vic.platform.dao.ResAccessDao;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.dao.RoleDao;
import com.ceit.vic.platform.models.ButtonLinkDTO;
import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.models.Person_Role;
import com.ceit.vic.platform.models.ResAccess;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.RoleDTO;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.Dep_PersonService;
import com.ceit.vic.platform.service.Person_RoleService;
import com.ceit.vic.platform.service.ResAccessService;
import com.ceit.vic.platform.service.ResourcesService;
import com.ceit.vic.platform.service.RoleService;

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
	@Autowired
	DepPersonDao depPersonDao;
	@Autowired
	PersonDao personDao;
	@Autowired
	RoleDao roleDao;
	@Autowired 
	ResAccessService resAccessService;
	@Autowired 
	Dep_PersonService dep_PersonService;
	@Autowired 
	Person_RoleService person_RoleService;
	@Autowired
	RoleService roleService;
	public boolean isAuthOrNot(Person person,int resId){
		boolean flag=false;
		if (person==null||roleService.getRoleIdByPersonId(person.getId()).contains(0)){
			return true;
		}
		 //判断人
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("resId", resId);
        map1.put("accessType", 1);
        map1.put("accessId", person.getId());
        List<ResAccess> testList = resAccessService.getByParamMap(map1);
        if (null!=testList&&testList.size()>0) {
        	flag =true;
		}
        //判断部门
        List<Dep_Person> depList = dep_PersonService.getByPersonId(person.getId(),false);
        if (null!=depList&&depList.size()>0) {
        	List<Integer> depIdList = new ArrayList<Integer>();
	        for (Dep_Person dep_Person : depList) {
				depIdList.add(dep_Person.getDepId());
			}
	        Map<String, Object> map2 = new HashMap<String, Object>();
	        map2.put("resId", resId);
	        map2.put("accessType", 2);
	        map2.put("accessId", depIdList);
	        List<ResAccess> testList2 = resAccessService.getByParamMap(map2);
	        if (null!=testList2&&testList2.size()>0) {
	        	flag = true;
			}
		}
        
        //判断角色
        List<Person_Role> person_Roles = person_RoleService.getPersonRoleByPersonId(person.getId());
        if (null!=person_Roles&&person_Roles.size()>0) {
        	List<Integer> roleIds = new ArrayList<Integer>();
	        for (Person_Role person_Role : person_Roles) {
				roleIds.add(person_Role.getRoleId());
			}
	        Map<String, Object> map3 = new HashMap<String, Object>();
	        map3.put("resId", resId);
	        map3.put("accessType", 0);
	        map3.put("accessId", roleIds);
	        List<ResAccess> testList3 = resAccessService.getByParamMap(map3);
	        if (null!=testList3&&testList3.size()>0) {
	        	flag = true;
			}
		}
        return flag;
	}
	
	
	@Override
	public List<NavItem> getNavItems(Person person) {
		List<Resources> rsList = resourcesDao.getNavResources();
		List<NavItem> navItems = new ArrayList<NavItem>();
		for (Resources resource : rsList) {
			int resId = resource.getId();
	        if (isAuthOrNot(person,resId)) {
	        	NavItem item = new NavItem(resource.getLink(), resource.getName(),
						resource.getId());
				navItems.add(item);
			}
		}
		return navItems;
	}

	@Override
	public List<ZTreeNode> getResourcesTreeById(int id, boolean containId,
			boolean containDisable,Person person) throws Exception {
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		List<Object[]> rList = resourcesDao.getResourcesTreeById(id,
				containDisable);
		for (Object[] objects : rList) {
			if (isAuthOrNot(person, Integer.parseInt(objects[0].toString()))) {
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
		if (null==depList) {
			return null;
		}
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
			if (null!=resources.getIcon()) {
				node.setIcon("static/easyui/themes/icons/"+resources.getIcon());
				node.setIconCls(resources.getIcon().split("\\.")[0]);
			}
			if (containBtnLink) {
				node.setTitle(null==resources.getMemo()?resources.getName():resources.getMemo());
			}
			if(resources.getType().equals("0")) {
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

	@Override
	public int getAuthTotal(int resId,int accessType) {
		return resAccessDao.getAccessIdTotalByResId(resId,accessType);
	}

	@Override
	public List<RoleDTO> getRoleAuthList(int resId, int page, int rows) {
		int firstResult, maxResults;
		firstResult = (page - 1) * rows;
		maxResults = rows;
		// 获得已授权的部门id集合
		List<Integer> idList = resAccessDao.getAccessIdsByResId(resId, 0,
				firstResult, maxResults);
		List<Role> roleList = roleDao.getRolesByIds(idList);
		if (roleList==null) {
			return null;
		}
		List<RoleDTO> dtoList = new ArrayList<RoleDTO>();
		for (Role role : roleList) {
			RoleDTO dto = new RoleDTO(role.getId(), role.getName(), role.getMemo());
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<PersonDTO> getPersonAuthList(int resId, int page, int rows) {
		int firstResult, maxResults;
		firstResult = (page - 1) * rows;
		maxResults = rows;
		// 获得已授权的人员id集合
		List<Integer> idList = resAccessDao.getAccessIdsByResId(resId, 1,
				firstResult, maxResults);
		List<Person> personList = personDao.getPersonsByIds(idList);
		if (null==personList) {
			return null;
		}
		List<PersonDTO> dtoList = new ArrayList<PersonDTO>();
		for (Person person : personList) {
			PersonDTO dto = new PersonDTO(person.getId(),person.getName(),person.getCode(),person.getMemo());
			if (person.getSex()==null) {
				dto.setSex("未知");
			}else {
				dto.setSex(person.getSex().equals("1")?"男":"女");
			}
			int depId = depPersonDao.getMainByPersonId(person.getId()).getDepId();
			Department department = departmentDao.getDepartmentById(depId);
			if (null!=department) {
				String depName = department.getName();
				dto.setDepartmentName(depName);
			}else {
				dto.setDepartmentName("未知");
			}
			dtoList.add(dto);

		}
		return dtoList;
	}

	@Override
	public List<Resources> getByParamMap(Map<String, Object> map) {
		return resourcesDao.getByParamMap(map);
	}

	@Override
	public Resources getResourcesById(int parentId) {
		return resourcesDao.getResourceById(parentId);
	}

	@Override
	public List<Resources> getByLink(String link) {
		return resourcesDao.getResourceByLink(link);
	}


	@Override
	public List<Resources> getResources(int accessId, int accessType,
			int pageIndex, int pageSize) {
		return resourcesDao.getResourcesByAccessId(accessId, accessType, pageIndex, pageSize);
	}


	@Override
	public int getResourceCount(int accessId, int accessType) {
		return resourcesDao.getResourcesCountByAccessId(accessId, accessType);
	}

}
