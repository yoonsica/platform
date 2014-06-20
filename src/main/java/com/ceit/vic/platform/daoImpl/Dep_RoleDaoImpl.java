package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.Dep_RoleDao;
import com.ceit.vic.platform.models.Dep_Role;
@Repository
public class Dep_RoleDaoImpl implements Dep_RoleDao{
	@Autowired
	private SessionFactory sf;

	@Override
	public List<Integer> getDepIdsByRoleId(int roleId, int firstResult,
			int maxResults) {
		StringBuffer sb = new StringBuffer("select t.depId from Dep_Role t where t.roleId=");
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
	public int getDepAmountByRoleId(int roleId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from Dep_Role t where t.roleId=");
		sb.append(roleId).append(" order by t.depId");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void removeByRoleIdDepId(int roleId, String[] depId) {
		List<Dep_Role> list = getPersonRoleByIds(roleId, depId);
		for (Dep_Role dep_Role : list) {
			sf.getCurrentSession().delete(dep_Role);
		}
	}

	@Override
	public List<Dep_Role> getDepRole(int roleId, int depId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Dep_Role t where t.roleId=");
		sb.append(roleId).append(" and t.depId=").append(depId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(Dep_Role dep_Role) {
		try {
			sf.getCurrentSession().save(dep_Role);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Dep_Role> getByDepId(int depId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Dep_Role t where t.depId=");
		sb.append(depId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteByDepIds(int[] idArray) {
		if (idArray!=null) {
			StringBuffer sb = new StringBuffer("delete Dep_Role t where t.depId in(");
			for (int i = 0; i < idArray.length-1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length-1]).append(")");
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
	}

	@Override
	public void removeUnusedRoles(int[] roleIds, int depId) {
		if (roleIds!=null) {
			StringBuffer sb = new StringBuffer("delete Dep_Role t where t.depId=");
			sb.append(depId).append(" and t.roleId not in(");
			for (int i = 0; i < roleIds.length-1; i++) {
				sb.append(roleIds[i]).append(",");
			}
			sb.append(roleIds[roleIds.length-1]).append(")");
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
	}

	public List<Dep_Role> getPersonRoleByIds(int roleId,String[] depId){
		Query query = null;
		StringBuffer sb = new StringBuffer("from Dep_Role t where t.roleId=");
		sb.append(roleId).append(" and t.depId in(");
		for (String id : depId) {
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
	
}
