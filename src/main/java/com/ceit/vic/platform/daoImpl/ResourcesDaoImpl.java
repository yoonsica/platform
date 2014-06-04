package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.ResourcesDao;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.Role;

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
		StringBuffer sb = new StringBuffer("select * from Resources t where t.type='0' ");
		if (!containDisable) {
			sb.append("and t.state='1' ");
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
		.append(" order by t.dispIndex");
		Query query = sf.getCurrentSession().createQuery(sb.toString());
		return (Resources) query.list().get(0);
	}


	@Override
	public void add(Resources resources) {
		sf.getCurrentSession().save(resources);
	}

	@Override
	public void remove(int moduleId) {
		sf.getCurrentSession().delete(getResourceById(moduleId));
	}

	@Override
	public List<Resources> getButtonLinkByResId(int resId, int firstResult,
			int maxResults) {
		Query query;
		StringBuffer sb = new StringBuffer("from Resources t where t.parentId =");
		sb.append(resId)
		.append(" and t.type in(1,2) order by t.dispIndex");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			return query.list();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public int getButtonLinkAmount(int resId) {
		Query query;
		StringBuffer sb = new StringBuffer("select count(*) from Resources t where t.parentId =");
		sb.append(resId)
		.append(" and t.type in(1,2)");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public void remove(int[] idArray) {
		if (idArray!=null) {
			StringBuffer sb = new StringBuffer("delete Resources t where t.id in(");
			for (int i = 0; i < idArray.length-1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length-1]).append(") ");
			try {
				sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Resources> getAllResources(boolean containBtnLink) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Resources t where t.state='1' ");
		if (!containBtnLink) {
			sb.append("and t.type='0' ");
		}
		sb.append("order by t.dispIndex");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());return query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Resources> getByParamMap(Map<String, Object> paraMap) {
		Query query;
		StringBuffer sb = new StringBuffer("from Resources t ");
		if (paraMap!=null) {
			sb.append("where ");
			for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
				if(entry.getValue() instanceof List){
					sb.append("t.").append(entry.getKey()).append("in(");
					List<Object> list = new ArrayList<Object>();
					if (null!=list) {
						for (Object object : list) {
							sb.append("'").append(object.toString()).append("',");
						}
						sb=new StringBuffer(sb.substring(0,sb.length()-1));
						sb.append(") and ");
					}
					
				}else {
					sb.append("t.").append(entry.getKey()).append("='").append(entry.getValue().toString()).append("' and ");
				}
			}
			try {
				String sql = sb.substring(0, sb.length()-4).toString();
				query = sf.getCurrentSession().createQuery(sql);
				return query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Resources> getResourceByLink(String link) {
		try {
			StringBuffer sb = new StringBuffer("from Resources t where instr('");
			sb.append(link).append("',t.link )>0");
			Query query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<Resources> getResourcesByAccessId(int accessId, int accessType,
			int pageIndex, int pageSize) {
		Query query;
		StringBuffer sb = new StringBuffer();
		sb.append("select r.id, r.name, r.memo, r.state from ResAccess t, Resources r where t.resId = r.id and t.accessType=")
				.append(accessType).append(" and t.accessId=").append(accessId)
				.append(" order by t.accessId");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Object[]> resourceObjs = query.list();
			List<Resources> resources = new ArrayList<Resources>();
			for(Object[] resourceObj : resourceObjs) {
				Resources r = new Resources();
				r.setId(Integer.parseInt(String.valueOf(resourceObj[0])));
				r.setName((String) resourceObj[1]);
				r.setMemo((String) resourceObj[2]);
				r.setState((String) resourceObj[3]);
				
				resources.add(r);
			}
			
			return resources;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getResourcesCountByAccessId(int accessId, int accessType) {
		Query query;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ResAccess t where t.accessType=")
				.append(accessType).append(" and t.accessId=").append(accessId)
				.append(" order by t.accessId");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (Exception e) {
		}
		return 0;
	}

}
