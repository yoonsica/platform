package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.Person_RoleDao;
@Repository
public class Person_RoleDaoImpl implements Person_RoleDao{
	@Autowired
	private SessionFactory sf;

	@Override
	public List<Integer> getPersonIdsByRoleId(int roleId,int firstResult,int maxResults) {
		StringBuffer sb = new StringBuffer("select t.personId from Person_Role t where t.roleId=");
		sb.append(roleId);
		Query query;
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			return query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getPersonAmountByRoleId(int roleId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from Person_Role t where t.roleId=");
		sb.append(roleId).append(" order by t.personId");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
