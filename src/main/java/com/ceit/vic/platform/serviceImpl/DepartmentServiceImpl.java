package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	IDPROVIDERDao idproviderDao;
	@Override
	public List<ZTreeNode> getDepartmentTree() {
		List<Department> depList = departmentDao.getAllDepartments();
		// TODO 通过depList组织树，返回树节点集合
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (Department department : depList) {
			ZTreeNode node = new ZTreeNode();
			node.setId(department.getId());
			node.setpId(department.getParentId());
			node.setName(department.getName());
			node.setIsParent("true");
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public int addDepartment(Department department) {
		int id = idproviderDao.getCurrentId("DEPARTMENT");
		department.setId(id);
		department.setDispindex(id);
		departmentDao.insertDepartment(department);
		idproviderDao.add("DEPARTMENT");
		return id;
	}

	@Override
	public List<ZTreeNode> getDepartmentTreeById(int id) {
		List<Object[]> depList = departmentDao.getDepartmentTreeById(0);
		// TODO 通过depList组织树，返回树节点集合
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		try {
			for (Object[] department : depList) {
				ZTreeNode node = new ZTreeNode();
				node.setId(Integer.valueOf(department[0].toString()));
				node.setpId(Integer.valueOf(department[4].toString()));
				node.setName(department[3].toString());
				//node.setIsParent("true");
				node.setOpen(node.getId()==4);
				nodeList.add(node);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeList;
	}

	@Override
	public Department getDepartmentById(int id) {
		return departmentDao.getDepartmentById(id);
	}

	@Override
	public String remove(int depId) {
		Department department = departmentDao.getDepartmentById(depId);
		if (department.getRemovable().equals("true")) {
			departmentDao.delete(depId);
			return "删除成功！";
		}else {
			return "不允许删除！";
		}
		
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}

	@Override
	public void up(int depId) {
		Department dep1 = departmentDao.getDepartmentById(depId);
		Department dep2 = departmentDao.getDepartmentToDown(dep1.getParentId(),dep1.getDispindex());
		int tmp = dep2.getDispindex();
		dep2.setDispindex(dep1.getDispindex());
		dep1.setDispindex(tmp);
		departmentDao.update(dep1);
		departmentDao.update(dep2);
	}

	@Override
	public void down(int depId) {
		Department dep1 = departmentDao.getDepartmentById(depId);
		Department dep2 = departmentDao.getDepartmentToUp(dep1.getParentId(),dep1.getDispindex());
		int tmp = dep2.getDispindex();
		dep2.setDispindex(dep1.getDispindex());
		dep1.setDispindex(tmp);
		departmentDao.update(dep1);
		departmentDao.update(dep2);
		
	}

	@Override
	public Department getDepartmentByPersonId(int personId) {
		return departmentDao.getDepartmentByPersonId(personId);
	}

	@Override
	public int getDepartmentCountByResourceId(int id) {
		return departmentDao.getDepartmentCountByResourceId(id);
	}

	@Override
	public List<Department> getDepartmentsByResourceId(int id, int pageIndex, int pageSize) {
		return departmentDao.getDepartmentsByResourceId(id, pageIndex, pageSize);
	}


}
