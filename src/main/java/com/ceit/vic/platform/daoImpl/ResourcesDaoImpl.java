package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.models.Resources;

@Repository
public class ResourcesDaoImpl implements ResourcesDao {
	@Autowired
	private SessionFactory sf;

	@SuppressWarnings("unchecked")
	@Override
	public List<Resources> getNavResources() {
		Query query = null;
		try {
			// id=0的节点为根节点，不显示，所有parentId=0的节点为目录条目,比如系统管理
			query = sf
					.getCurrentSession()
					.createQuery(
							"from Resources t where t.parentId=1 and t.state=1 order by t.dispIndex");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return query.list();
	}

	@Override
	public List<Object[]> getResourcesTreeById(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createSQLQuery(
					"select * from Resources t start with t.id=" + id
							+ " connect by t.parentid = prior t.id order by t.dispIndex");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return query.list();
	}


	@Override
	public Resources getResourceById(int id) {
		Query query = sf.getCurrentSession().createQuery("from Resources t where t.id="+id);
		return (Resources) query.uniqueResult();
	}

}
