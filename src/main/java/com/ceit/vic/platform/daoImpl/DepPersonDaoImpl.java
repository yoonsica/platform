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
	public List<Person> getPersonsByDepId(int depId) {
		Query query = null;
		StringBuffer sb = new StringBuffer("from Dep_Person t where t.depId=");
		sb.append(depId).append(" order by t.personId");
		List<Dep_Person> list=new ArrayList<Dep_Person>();
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
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
}