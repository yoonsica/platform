package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.Dep_PersonDao;
import com.ceit.vic.platform.dao.Dep_RoleDao;
import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.PersonDao;
import com.ceit.vic.platform.dao.Person_RoleDao;
import com.ceit.vic.platform.dao.RoleDao;
import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Dep_Role;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.models.Person_Role;
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
	DepartmentDao departmentDao;
	@Autowired
	Dep_PersonDao dep_PersonDao;
	@Autowired
	Person_RoleDao person_RoleDao;
	@Autowired
	Dep_RoleDao dep_RoleDao;
	@Autowired
	PersonDao personDao;
	@Override
	public List<ZTreeNode> getRoleTree() {
		List<Role> roleList = roleDao.getRolesTree();
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (Role role : roleList) {
			ZTreeNode node = new ZTreeNode();
			if (role.getIsFolder()==1) {
				node.setIsParent("true");
			}else {
				node.setIsParent("false");
			}
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
	public String delete(int id) {
		if (roleDao.getRoleById(id).getRemovable().equals("true")) {
			roleDao.remove(id);
			return "删除成功！";
		}else {
			return "不允许删除！";
		}
		
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
	public List<PersonDTO> getPersonsByRoleId(int roleId,int page,int rows) {
		int firstResult,maxResults;
		firstResult = (page-1)*rows;
		maxResults = rows;
		List<Integer> personIds = person_RoleDao.getPersonIdsByRoleId(roleId,firstResult,maxResults);
		List<Person> list1 =  personDao.getPersonsByIds(personIds);
		List<PersonDTO> list2 = new ArrayList<PersonDTO>();
		for (Person person : list1) {
			PersonDTO dto = new PersonDTO();
			dto.setId(person.getId());
			dto.setCode(person.getCode());
			Dep_Person dep_Person = dep_PersonDao.getByPersonId(person.getId(),true).get(0);
			dto.setDepartmentName(departmentDao.getDepartmentById(dep_Person.getDepId()).getName());
			dto.setState(person.getState().equals("0")?"启用":"停用");
			dto.setMemo(person.getMemo());
			dto.setName(person.getName());
			list2.add(dto);
		}
		return list2;
	}
	@Override
	public int getPersonsAmountByRoleId(int roleId) {
		return person_RoleDao.getPersonAmountByRoleId(roleId);
	}
	
	@Override
	public int getDepsAmountByRoleId(int roleId) {
		return dep_RoleDao.getDepAmountByRoleId(roleId);
	}
	
	@Override
	public void canclePersonRole(int roleId, String[] personId) {
		person_RoleDao.removeByRoleIdPersonId(roleId,personId);
		
	}
	@Override
	public void addPersonRole(int[] roleIds, int personId) {
		person_RoleDao.removeUnusedRoles(roleIds,personId);
		for (int roleId : roleIds) {
				if(person_RoleDao.getPersonRole(roleId,personId).size()!=1){
					int id = idproviderDao.getCurrentId("PERSONROLE");
					Person_Role person_Role = new Person_Role();
					person_Role.setPersonId(personId);
					person_Role.setRoleId(roleId);
					person_Role.setId(id);
					person_RoleDao.add(person_Role);
					idproviderDao.add("PERSONROLE");
				}
		}
		
	}
	@Override
	public List<Role> getAllRoles() {
		return roleDao.getRolesTree();
	}
	@Override
	public List<Integer> getRoleIdByPersonId(int personId) {
		List<Person_Role> list = person_RoleDao.getPersonRoleByPersonId(personId);
		List<Integer> list1 = new ArrayList<Integer>();
		for (Person_Role person_Role : list) {
			list1.add(person_Role.getRoleId());
		}
		return list1;
	}

	@Override
	public List<Role> getRolesByPersonId(int personId) {
		List<Person_Role> list = person_RoleDao.getPersonRoleByPersonId(personId);
		List<Integer> list1 = new ArrayList<Integer>();
		for (Person_Role person_Role : list) {
			list1.add(person_Role.getRoleId());
		}
		return roleDao.getRolesByIds(list1);
	}
	@Override
	public void addPersonRole(int roleId, int[] personIds) {
		for (int personId : personIds) {
			List<Person_Role> list = person_RoleDao.getPersonRole(roleId,personId);
				if(null==list||list.size()==0){
					int id = idproviderDao.getCurrentId("PERSONROLE");
					Person_Role person_Role = new Person_Role();
					person_Role.setPersonId(personId);
					person_Role.setRoleId(roleId);
					person_Role.setId(id);
					person_RoleDao.add(person_Role);
					idproviderDao.add("PERSONROLE");
				}
		}
		
	}
	@Override
	public int getRoleCountByPersonId(int personId) {
		return roleDao.getRoleCountByPersonId(personId);
	}
	@Override
	public List<Role> getRolesByPersonId(int personId, int pageIndex,
			int pageSize) {
		return roleDao.getRolesByPersonId(personId, pageIndex, pageSize);
	}
	@Override
	public List<Role> getRolesByResourceId(int id, int pageIndex, int pageSize) {
		return roleDao.getRolesByResourceId(id, pageIndex, pageSize);
	}
	@Override
	public int getRoleCountByResourceId(int id) {
		return roleDao.getRoleCountByResourceId(id);
	}
	@Override
	public void addDepRole(int roleId, int[] idArray) {
		for (int depId : idArray) {
			List<Dep_Role> list = dep_RoleDao.getDepRole(roleId, depId);
				if(null==list||list.size()==0){
					int id = idproviderDao.getCurrentId("DEPROLE");
					Dep_Role dep_Role = new Dep_Role();
					dep_Role.setDepId(depId);
					dep_Role.setRoleId(roleId);
					dep_Role.setId(id);
					dep_RoleDao.add(dep_Role);
					idproviderDao.add("DEPROLE");
				}
		}
		
	}
	@Override
	public List<DepDTO> getDepsByRoleId(int roleId, int page, int rows) {
		int firstResult,maxResults;
		firstResult = (page-1)*rows;
		maxResults = rows;
		List<Integer> depIds = dep_RoleDao.getDepIdsByRoleId(roleId, firstResult, maxResults);
		List<Department> list1 =  departmentDao.getDepartmentByIds(depIds);
		List<DepDTO> list2 = new ArrayList<DepDTO>();
		for (Department department : list1) {
			DepDTO dto = new DepDTO();
			dto.setId(department.getId());
			dto.setName(department.getName());
			dto.setMemo(department.getMemo());
			list2.add(dto);
		}
		return list2;
	}
}
