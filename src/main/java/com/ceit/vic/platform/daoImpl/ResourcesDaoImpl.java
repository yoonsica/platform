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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getResourcesTreeById(int id,boolean containDisable) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select * from Resources t ");
		if (!containDisable) {
			sb.append(" where t.state='1' ");
		}
		sb.append("start with t.id=").append(id)
		.append(" connect by t.parentid = prior t.id order by t.dispIndex");
		try {
			query = sf.getCurrentSession().createSQLQuery(sb.toString());
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

	@Override
	public void update(Resources resources) {
		sf.getCurrentSession().update(resources);
	}

	@Override
	public Resources getResourceToDown(int parentId, int dispIndex) {
		StringBuffer sb = new StringBuffer("from Resources t where t.parentId=");
		sb.append(parentId).append(" and t.dispIndex<").append(dispIndex)
		.append(" order by t.dispIndex desc");
		Query query = sf.getCurrentSession().createQuery(sb.toString());
		return (Resources) query.list().get(0);
	}

	@Override
	public Resources getResourceToUp(int parentId, int dispIndex) {
		StringBuffer sb = new StringBuffer("from Resources t where t.parentId=");
		sb.append(parentId).append(" and t.dispIndex>").append(dispIndex)
		.append(" order by t.dispIndex desc");
		Query query = sf.getCurrentSession().createQuery(sb.toString());
		return (Resources) query.list().get(0);
	}

}
