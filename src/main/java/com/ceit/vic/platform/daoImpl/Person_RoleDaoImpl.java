package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.Person_RoleDao;
import com.ceit.vic.platform.models.Person_Role;
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

	public List<Person_Role> getPersonRoleByIds(int roleId,String[] personId){
		Query query = null;
		StringBuffer sb = new StringBuffer("from Person_Role t where t.roleId=");
		sb.append(roleId).append(" and t.personId in(");
		for (String id : personId) {
			sb.append(id).append(",");
		}
		sb = new StringBuffer(sb.substring(0,sb.length()-1));
		sb.append(")");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void removeByRoleIdPersonId(int roleId, String[] personId) {
		List<Person_Role> list = getPersonRoleByIds(roleId, personId);
		for (Person_Role person_Role : list) {
			sf.getCurrentSession().delete(person_Role);
		}
		
	}

	@Override
	public List<Person_Role> getPersonRole(int roleId, int personId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Person_Role t where t.roleId=");
		sb.append(roleId).append(" and t.personId=").append(personId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(Person_Role person_Role) {
		try {
			sf.getCurrentSession().save(person_Role);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Person_Role> getPersonRoleByPersonId(int personId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Person_Role t where t.personId=");
		sb.append(personId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void deleteByPersonIds(int[] idArray) {
		if (idArray!=null) {
			StringBuffer sb = new StringBuffer("delete Person_Role t where t.personId in(");
			for (int i = 0; i < idArray.length-1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length-1]).append(")");
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
		
	}

	@Override
	public void removeUnusedRoles(int[] roleIds, int personId) {
		if (roleIds!=null) {
			StringBuffer sb = new StringBuffer("delete Person_Role t where t.personId=");
			sb.append(personId).append(" and t.roleId not in(");
			for (int i = 0; i < roleIds.length-1; i++) {
				sb.append(roleIds[i]).append(",");
			}
			sb.append(roleIds[roleIds.length-1]).append(")");
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
		
	}

}
