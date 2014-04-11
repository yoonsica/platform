package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
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
			query = sf.getCurrentSession().createQuery("from Department order by parentId,dispindex");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Department> list=new ArrayList<Department>();
		try {
			list = query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insertDepartment(Department department) {
		sf.getCurrentSession().save(department);
	}

	@Override
	public List<Object[]> getDepartmentTreeById(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createSQLQuery("select * from DEPARTMENT t start with t.id=4 connect by t.parentid = prior t.id order by t.parentid,t.dispIndex");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			list = query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Department getDepartmentById(int id) {
		Query query = null;
		Department department = null;
		try {
			query = sf.getCurrentSession().createQuery("from Department t where t.id="+id);
			department = (Department) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return department;
	}

}
