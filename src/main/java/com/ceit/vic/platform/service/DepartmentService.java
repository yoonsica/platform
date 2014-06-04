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
	/**
	 * 添加部门
	 * @param department
	 * @return 新添加的部门的id
	 */
	@Transactional
	public int addDepartment(Department department);
	@Transactional(readOnly=true)
	public Department getDepartmentById(int id);
	@Transactional
	public Department getDepartmentByPersonId(int personId);
	@Transactional
	public void remove(int depId);
	@Transactional
	public void update(Department department);
	@Transactional
	public void up(int depId);
	@Transactional
	public void down(int depId);
	@Transactional
	public int getDepartmentCountByResourceId(int id);
	@Transactional
	public List<Department> getDepartmentsByResourceId(int id, int pageIndex, int pageSize);
}
