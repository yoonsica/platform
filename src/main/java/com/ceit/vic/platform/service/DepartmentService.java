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
	public boolean insertDepartment(Department department);
}
