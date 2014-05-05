package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.DepPersonDao;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Person;
@Repository
public class DepPersonDaoImpl implements DepPersonDao{
	@Autowired
	private SessionFactory sf;

	@Override
	public List<Person> getPersonsByDepId(int depId,int firstResult,int maxResults) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Dep_Person t where t.depId=");
		sb.append(depId).append(" order by t.personId");
		List<Dep_Person> list=new ArrayList<Dep_Person>();
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		List<Person> personList = new ArrayList<Person>();
		if (list.size()>0) {
			sb = new StringBuffer("from Person t where t.id in(");
			for (Dep_Person dep_Person : list) {
				sb.append(dep_Person.getPersonId()).append(",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			sb.append(") order by t.dispIndex");
			try {
				query = sf.getCurrentSession().createQuery(sb.toString());
				personList = query.list();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return personList;
	}

	@Override
	public int getTotalPersonByDepId(int depId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from Dep_Person t where t.depId=");
		sb.append(depId).append(" order by t.personId");
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Dep_Person> getByPersonId(int personId) {
		Query query = null;
		StringBuffer sb = new StringBuffer(" from Dep_Person t where t.personId=");
		sb.append(personId);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}