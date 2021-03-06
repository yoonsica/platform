package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.RoleDao;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private SessionFactory sf;


	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesTree() {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Role t where t.id>-3 order by t.dispIndex");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Role getRoleById(int id) {
		Query query = sf.getCurrentSession().createQuery("from Role t where t.id="+id);
		return (Role) query.uniqueResult();
	}

	@Override
	public void update(Role role) {
		sf.getCurrentSession().update(role);
	}


	@Override
	public void add(Role role) {
		sf.getCurrentSession().save(role);
	}

	@Override
	public void remove(int roleId) {
		sf.getCurrentSession().delete(getRoleById(roleId));
	}


	@Override
	public List<Role> getRolesByIds(List<Integer> idList) {
		if (idList==null||idList.size()==0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("from Role t where t.id in(");
		for (int i = 0; i < idList.size()-1; i++) {
			sb.append(idList.get(i)).append(",");
		}
		sb.append(idList.get(idList.size()-1))
		.append(") order by t.dispIndex");
		Query query;
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByPersonId(int personId, int pageIndex, int pageSize) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select r.id, r.name, r.memo from Person_Role t, Role r where r.id = t.roleId and t.personId=");
		sb.append(personId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Object[]> roleObjs = query.list();
			List<Role> roles = new ArrayList<Role>();
			for(Object[] roleObj : roleObjs) {
				Role r = new Role();
				r.setId(Integer.parseInt(String.valueOf(roleObj[0])));
				r.setName((String) roleObj[1]);
				r.setMemo((String) roleObj[2]);
				
				roles.add(r);
			}
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int getRoleCountByPersonId(int personId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from Person_Role t where t.personId=");
		sb.append(personId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public List<Role> getRolesByResourceId(int id, int pageIndex, int pageSize) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select t1.id, t1.name, t1.memo from ResAccess t, Role t1 where t1.id = t.accessId and t.resId=" + id);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Object[]> roleObjs = query.list();
			List<Role> roles = new ArrayList<Role>();
			for(Object[] roleObj : roleObjs) {
				Role r = new Role();
				r.setId(Integer.parseInt(String.valueOf(roleObj[0])));
				r.setName((String) roleObj[1]);
				r.setMemo((String) roleObj[2]);
				
				roles.add(r);
			}
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int getRoleCountByResourceId(int id) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from ResAccess t where t.accessType = 0 and t.resId=" + id);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
