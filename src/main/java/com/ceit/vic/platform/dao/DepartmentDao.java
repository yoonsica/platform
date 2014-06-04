package com.ceit.vic.platform.dao;

import java.util.List;
import java.util.Map;

import com.ceit.vic.platform.models.Department;

public interface DepartmentDao {
	public List<Department> getAllDepartments();
	public List<Object[]> getDepartmentTreeById(int id);
	public void insertDepartment(Department department);
	public Department getDepartmentById(int id);
	public Department getDepartmentByPersonId(int personId);
	
	public void delete(int id);
	public void update(Department department);
	public Department getDepartmentToDown(int parentId, int dispindex);
	public Department getDepartmentToUp(int parentId, int dispindex);
	public List<Department> getDepartmentsByIds(List<Integer> idList);
	public List<Department> getByParamMap(Map<String, Object> paraMap);
	public int getDepartmentCountByResourceId(int id);
	public List<Department> getDepartmentsByResourceId(int id, int pageIndex, int pageSize);
	
}
