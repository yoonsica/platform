package com.ceit.vic.platform.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.LogDao;
import com.ceit.vic.platform.models.Log;

@Repository
public class LogDaoImpl implements LogDao {

	@Autowired
	private SessionFactory sf;
	
	@Override
	public void add(Log log) {
		sf.getCurrentSession().save(log);
	}

	@Override
	public void delete(List<Integer> ids) {
		Query query = sf.getCurrentSession().createQuery("delete from Log where id in :ids");
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> find(int typeId, String content,
			String username, String ip, Date beginTime, Date endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Log where recordTime between :beginTime and :endTime");
		if(null != content && !"".equals(content)){  
			sql.append(" and content like '%" + content + "%'");  
        }
		if(null != username && !"".equals(username)){  
			sql.append(" and username like '%" + username + "%'");  
        }
		if(null != ip && !"".equals(ip)){  
			sql.append(" and ip like '%" + ip + "%'");  
        }
		Query query = sf.getCurrentSession().createQuery(sql.toString());
		query.setParameter("beginTime", beginTime);
		query.setParameter("endTime", endTime);
		return query.list();
	}

}
