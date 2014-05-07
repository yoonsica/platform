package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.RoleDao;
import com.ceit.vic.platform.models.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private SessionFactory sf;


	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesTree() {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Role t where t.id>-1 order by t.dispIndex");
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

}
