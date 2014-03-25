package com.ceit.vic.platform.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceit.vic.platform.dao.NativeQueryDao;

@Component("nativeQueryDao")
public class NativeQueryDaoImpl implements NativeQueryDao {

	@Autowired
	private SessionFactory sf;
	@Override
	public Object nativeQuery(String sql) {
		Session session = sf.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list();
	}
	@Override
	public Object nativeQueryByPage(int pageNo, int pageSize, String sqlStr) {
		Session session=null;
		if (null==sf.getCurrentSession()) {
			session = sf.openSession();
		}else {
			session = sf.getCurrentSession();
		}
		Query query = session.createSQLQuery(sqlStr);
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

}
