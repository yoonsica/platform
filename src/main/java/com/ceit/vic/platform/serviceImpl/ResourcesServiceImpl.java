package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.ResourcesService;
@Service
public class ResourcesServiceImpl implements ResourcesService {
	@Autowired
	ResourcesDao resourcesDao;

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
	public List<ZTreeNode> getResourcesTreeById(int id) throws Exception{
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		List<Object[]> rList = resourcesDao.getResourcesTreeById(id);
		for (Object[] objects : rList) {
			if (objects[10].toString().equals("1")) {
				ZTreeNode node = new ZTreeNode();
				node.setId(Integer.parseInt(objects[0].toString()));
				node.setName(objects[8].toString());
				node.setpId(Integer.parseInt(objects[9].toString()));
				if (null!=objects[3]) {
					node.setIcon(objects[3].toString());
				}
				if (objects[6].toString().equals("1")||objects[6].toString().equals("0")) {//1-目录
					node.setParent(true);
				}else if(objects[6].toString().equals("2")) {
					node.setHref(objects[4].toString());//链接
				}
				
				//node.setIcon(objects[3].toString());
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	

}
