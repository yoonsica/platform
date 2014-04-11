package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Department;

public interface DepartmentDao {
	public List<Department> getAllDepartments();
	public List<Object[]> getDepartmentTreeById(int id);
	public void insertDepartment(Department department);
	public Department getDepartmentById(int id);
}
