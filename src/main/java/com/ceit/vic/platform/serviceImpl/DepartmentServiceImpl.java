package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDao departmentDao;
	@Override
	public List<ZTreeNode> getDepartmentTree() {
		List<Department> depList = departmentDao.getAllDepartments();
		// TODO 通过depList组织树，返回树节点集合
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (Department department : depList) {
			if (department.getId()!=0) {
				ZTreeNode node = new ZTreeNode();
				node.setId(department.getId());
				node.setpId(department.getParentId());
				node.setName(department.getName());
				node.setIsParent("true");
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	@Override
	public boolean insertDepartment(Department department) {
		// TODO Auto-generated method stub
		return false;
	}


}
