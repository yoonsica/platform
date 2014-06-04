package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.LogDao;
import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;

@Repository
public class LogDaoImpl implements LogDao {

	@Autowired
	private SessionFactory sf;
	
	@Override
	public void add(Log log) {
		log.setRecordTime(new Date());
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
	public Map<String, Object> find(int typeId, String content,
			String personName, String ip, Date beginTime, Date endTime, 
			int pageIndex, int pageSize, String[] sortNames, String[] sortOrders) {
		StringBuffer sqlCondition = new StringBuffer();
		sqlCondition.append("where recordTime between :beginTime and :endTime");
		if(null != content && !"".equals(content)){  
			sqlCondition.append(" and content like '%" + content + "%'");  
        }
		if(0 != typeId) {
			sqlCondition.append(" and type.id = '" + typeId + "'");  
		}
		if(null != personName && !"".equals(personName)){  
			sqlCondition.append(" and person.name like '%" + personName + "%'");  
        }
		if(null != ip && !"".equals(ip)){  
			sqlCondition.append(" and ip like '%" + ip + "%'");  
        }
		
		if(sortNames != null && sortNames.length > 0) {
			sqlCondition.append(" order by ");
			for(int i=0;i<sortNames.length;i++) {
				if(sortNames[i].equals("type"))
					sqlCondition.append("type.name");
				else if(sortNames[i].equals("person"))
					sqlCondition.append("person.name");
				else
					sqlCondition.append(sortNames[i]);
				sqlCondition.append(" " + sortOrders[i] + ",");
			}
			sqlCondition.delete(sqlCondition.length() - 1, sqlCondition.length());
		}
		
		Map<String, Object> logResult = new HashMap<String, Object>();
		try {
			Query query = sf.getCurrentSession().createQuery("from Log " + sqlCondition.toString());
			query.setParameter("beginTime", beginTime);
			query.setParameter("endTime", endTime);
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Log> logs = query.list();
			
			query = sf.getCurrentSession().createQuery("select count(*) from Log " + sqlCondition.toString());
			query.setParameter("beginTime", beginTime);
			query.setParameter("endTime", endTime);
			int logCount = Integer.parseInt(String.valueOf(query.uniqueResult()));
			
			logResult.put("rows", logs);
			logResult.put("total", logCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> find(int personId, int pageIndex, int pageSize) {
		StringBuffer sqlCondition = new StringBuffer();
		sqlCondition.append("where person.id = :personId order by id");
		Map<String, Object> logResult = new HashMap<String, Object>();
		try {
			Query query = sf.getCurrentSession().createQuery("from Log " + sqlCondition.toString());
			query.setParameter("personId", personId);
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Log> logs = query.list();
			
			query = sf.getCurrentSession().createQuery("select count(*) from Log " + sqlCondition.toString());
			query.setParameter("personId", personId);
			int logCount = Integer.parseInt(String.valueOf(query.uniqueResult()));
			
			logResult.put("rows", logs);
			logResult.put("total", logCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LogType> findAllTypes() {
		List<LogType> logs = new ArrayList<LogType>();
		try {
			logs = sf.getCurrentSession().createQuery("from LogType").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return logs;
	}

}
