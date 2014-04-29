package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.PersonDao;
import com.ceit.vic.platform.dao.Person_RoleDao;
import com.ceit.vic.platform.dao.RoleDao;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;
	@Autowired
	IDPROVIDERDao idproviderDao;
	@Autowired
	Person_RoleDao person_RoleDao;
	@Autowired
	PersonDao personDao;
	@Override
	public List<ZTreeNode> getRoleTree() {
		List<Role> roleList = roleDao.getRolesTree();
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (Role role : roleList) {
			ZTreeNode node = new ZTreeNode();
			node.setId(role.getId());
			node.setpId(role.getParentId());
			node.setName(role.getName());
			nodeList.add(node);
		}
		return nodeList;
	}
	@Override
	public void update(Role role) {
		roleDao.update(role);
	}
	@Override
	public void delete(int id) {
		roleDao.remove(id);
	}
	@Override
	public int add(Role role) {
		int id = idproviderDao.getCurrentId("ROLE");
		role.setId(id);
		role.setDispIndex(id);
		roleDao.add(role);
		idproviderDao.add("ROLE");
		return id;
	}
	@Override
	public Role getRoleById(int id) {
		return roleDao.getRoleById(id);
	}
	@Override
	public List<Person> getPersonsByRoleId(int roleId,int page,int rows) {
		int firstResult,maxResults;
		firstResult = (page-1)*rows;
		maxResults = rows;
		List<Integer> personIds = person_RoleDao.getPersonIdsByRoleId(roleId,firstResult,maxResults);
		return personDao.getPersonsByIds(personIds);
	}
	@Override
	public int getPersonsAmountByRoleId(int roleId) {
		return person_RoleDao.getPersonAmountByRoleId(roleId);
	}

}
