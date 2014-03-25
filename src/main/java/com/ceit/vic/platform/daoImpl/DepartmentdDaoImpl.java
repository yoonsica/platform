package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.models.Department;
@Repository
public class DepartmentdDaoImpl implements DepartmentDao{
	@Autowired
	private SessionFactory sf;
	@Override
	public List<Department> getAllDepartments() {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Department");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query.list();
	}

	@Override
	public boolean insertDepartment(Department department) {
		// TODO Auto-generated method stub
		return false;
	}

}
