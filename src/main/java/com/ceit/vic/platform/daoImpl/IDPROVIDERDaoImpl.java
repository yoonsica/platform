package com.ceit.vic.platform.daoImpl;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.IDPROVIDERDao;

@Repository
public class IDPROVIDERDaoImpl implements IDPROVIDERDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public int getCurrentId(String tableName) {
		try {
			Query query = sf.getCurrentSession().createQuery("select t.id from IDPROVIDER t where t.name='"+tableName+"'");
			return Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void add(String tableName) {
		sf.getCurrentSession().createQuery("update IDPROVIDER t set t.id=t.id+1 where t.name='"+tableName+"'").executeUpdate();
	}


}
