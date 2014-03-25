package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Department;

public interface DepartmentDao {
	public List<Department> getAllDepartments();
	public boolean insertDepartment(Department department);
}
