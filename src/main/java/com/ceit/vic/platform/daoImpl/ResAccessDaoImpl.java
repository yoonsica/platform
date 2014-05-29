package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.ResAccessDao;
import com.ceit.vic.platform.models.ResAccess;

@Repository
public class ResAccessDaoImpl implements ResAccessDao {

	@Autowired
	SessionFactory sf;

	@Override
	public List<Integer> getAccessIdsByResId(int resId, int accessType,
			int firstResult, int maxResults) {
		Query query;
		StringBuffer sb = new StringBuffer();
		sb.append("select t.accessId from ResAccess t where t.accessType=")
				.append(accessType).append(" and t.resId=").append(resId)
				.append(" order by t.accessId");
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
	public void add(ResAccess resAccess) {
		try {
			sf.getCurrentSession().save(resAccess);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void deleteResAccess(int[] idArray, int resId, int accessType) {
		if (null == idArray || idArray.length == 0) {
		} else {
			StringBuffer sb = new StringBuffer(
					"delete ResAccess t where t.resId=");
			sb.append(resId).append(" and t.accessType=").append(accessType)
					.append(" and t.accessId in(");
			for (int i = 0; i < idArray.length - 1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length - 1]).append(")");
			try {
				sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@Override
	public int getAccessIdTotalByResId(int resId,int accessType) {
		Query query;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(t.accessId) from ResAccess t where t.accessType=")
				.append(accessType).append(" and t.resId=").append(resId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
		}
		return 0;
		
	}

	@Override
	public List<ResAccess> getByParamMap(Map<String, Object> paraMap) {
		Query query;
		StringBuffer sb = new StringBuffer("from ResAccess t ");
		if (paraMap!=null) {
			sb.append("where ");
			for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
				if(entry.getValue() instanceof List){
					List<Object> list = (List<Object>) entry.getValue();
					if (null!=list&&list.size()>0) {
						sb.append("t.").append(entry.getKey()).append(" in(");
						for (Object object : list) {
							sb.append("'").append(object.toString()).append("',");
						}
						sb=new StringBuffer(sb.substring(0,sb.length()-1));
						sb.append(") and ");
					}
					
				}else{
					sb.append("t.").append(entry.getKey()).append("=").append(entry.getValue().toString()).append(" and ");
				}
			}
		}
		try {
			query = sf.getCurrentSession().createQuery(sb.substring(0, sb.length()-4).toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
