package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.ResAccessDao;

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
		.append(accessType)
		.append(" and t.resId=")
		.append(resId)
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

}
