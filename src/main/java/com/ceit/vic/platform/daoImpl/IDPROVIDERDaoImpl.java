package com.ceit.vic.platform.daoImpl;


import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.models.IDPROVIDER;

@Repository
public class IDPROVIDERDaoImpl implements IDPROVIDERDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public int getCurrentId(String tableName) {
		Query query = sf.getCurrentSession().createQuery("select t.id from IDPROVIDER t where t.name='"+tableName+"'");
		return Integer.valueOf(query.uniqueResult().toString());
	}

	@Override
	public void add(String tableName) {
		sf.getCurrentSession().createQuery("update IDPROVIDER t set t.id=t.id+1 where t.name='"+tableName+"'").executeUpdate();
	}

	@Override
	public int generateId(Class<?> tableClass) {
		Table tableAnnotation = ((Table) tableClass.getClass().getAnnotation(Table.class));
		String tableName = tableAnnotation == null ? tableClass.getSimpleName().toUpperCase() : tableAnnotation.name().toUpperCase();
		Query query = sf.getCurrentSession().createQuery("select t.id from IDPROVIDER t where t.name= '" + tableName + "'");
		int id = Integer.valueOf(query.uniqueResult().toString());
		sf.getCurrentSession().createQuery("update IDPROVIDER t set t.id = t.id + 1 where t.name = '" + tableName + "'").executeUpdate();
		sf.getCurrentSession().flush();
		return id;
	}


}
