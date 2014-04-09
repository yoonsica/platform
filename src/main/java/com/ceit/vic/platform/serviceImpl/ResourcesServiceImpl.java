package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.ResourcesService;
@Service
public class ResourcesServiceImpl implements ResourcesService {
	@Autowired
	ResourcesDao resourcesDao;
	@Autowired
	IDPROVIDERDao idproviderDao;
	@Override
	public List<NavItem> getNavItems() {
		List<Resources> rsList = resourcesDao.getNavResources();
		List<NavItem> navItems = new ArrayList<NavItem>();
		for (Resources resource : rsList) {
			NavItem item = new NavItem(resource.getLink(),resource.getName(),resource.getId());
			navItems.add(item);
		}
		return navItems;
	}

	@Override
	public List<ZTreeNode> getResourcesTreeById(int id,boolean containId,boolean containDisable) throws Exception{
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		List<Object[]> rList=resourcesDao.getResourcesTreeById(id,containDisable);
		for (Object[] objects : rList) {
			ZTreeNode node = new ZTreeNode();
			node.setId(Integer.parseInt(objects[0].toString()));
			if (containId) {
				node.setName("("+node.getId()+")"+objects[8].toString());
			}else {
				node.setName(objects[8].toString());
			}
			node.setpId(Integer.parseInt(objects[9].toString()));
			if (null!=objects[3]) {
				node.setIcon("static/easyui/themes/icons/"+objects[3].toString());
				node.setIconCls(objects[3].toString().split("\\.")[0]);
			}
			if (objects[6].toString().equals("1")||objects[6].toString().equals("0")) {//1-目录
				node.setIsParent("true");
			}else if(objects[6].toString().equals("2")) {
				node.setHref(objects[4].toString());//链接
			}
			
			//node.setIcon(objects[3].toString());
			nodeList.add(node);
		}
		return nodeList;
	}
	
	@Override
	public ModuleInfoDTO getModuleInfoById(int moduleId) {
		Resources resources = resourcesDao.getResourceById(moduleId);
		//组织dto
		ModuleInfoDTO dto = new ModuleInfoDTO();
		dto.setId(resources.getId());
		dto.setName(resources.getName());
		dto.setLink(resources.getLink()==null?"":resources.getLink());
		dto.setIcon(resources.getIcon());
		if (resources.getId()==1) {
			dto.setParent("");
		}else {
			/*Resources parentResources = resourcesDao.getResourceById(resources.getParentId());
			dto.setParent(parentResources.getName());*/
			dto.setParent(String.valueOf(resources.getParentId()));
		}
		//dto.setState(resources.getState().equals("1")?"启用":"停用");
		dto.setState(resources.getState());
		return dto;
	}

	@Override
	public void updateResources(ModuleInfoDTO moduleInfo) {
		Resources resources = resourcesDao.getResourceById(moduleInfo.getId());
		resources.setName(moduleInfo.getName());
		resources.setLink(moduleInfo.getLink());
		resources.setIcon(moduleInfo.getIcon());
		resources.setState(moduleInfo.getState());
		if(!moduleInfo.getParent().equals("")){
			resources.setParentId(Integer.valueOf(moduleInfo.getParent()));
		}
		//dao中更新
		resourcesDao.update(resources);
	}

	@Override
	public void up(int moduleId) {
		Resources module1 = resourcesDao.getResourceById(Integer.valueOf(moduleId));
		Resources module2 = resourcesDao.getResourceToDown(module1.getParentId(),module1.getDispIndex());
		int tmp = module2.getDispIndex();
		module2.setDispIndex(module1.getDispIndex());
		module1.setDispIndex(tmp);
		resourcesDao.update(module1);
		resourcesDao.update(module2);
	}

	@Override
	public void down(int moduleId) {
		Resources module1 = resourcesDao.getResourceById(Integer.valueOf(moduleId));
		Resources module2 = resourcesDao.getResourceToUp(module1.getParentId(),module1.getDispIndex());
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
		resources.setType(0+"");
		resources.setState(1+"");
		if (isFolder) {
			resources.setmType(1+"");
		}else {
			resources.setmType(2+"");
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

}
