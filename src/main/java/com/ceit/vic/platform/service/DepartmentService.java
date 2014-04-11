package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ZTreeNode;

public interface DepartmentService {
	/**
	 * 获得部门树的list
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ZTreeNode> getDepartmentTree();
	@Transactional
	public List<ZTreeNode> getDepartmentTreeById(int id);
	@Transactional
	public int addDepartment(Department department);
	@Transactional(readOnly=true)
	public Department getDepartmentById(int id);
}
