package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ceit.vic.platform.dao.PersonDao;
import com.ceit.vic.platform.models.Person;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public void add(Person person) {
		sf.getCurrentSession().save(person);
		
	}

	@Override
	public void delete(int id) {
		sf.getCurrentSession().delete(getPersonById(id));
		
	}

	private Person getPersonById(int id) {
		Query query = sf.getCurrentSession().createQuery("from Person t where t.id="+id);
		return (Person) query.uniqueResult();
	}

	@Override
	public void update(Person person) {
		sf.getCurrentSession().update(person);
	}

	@Override
	public List<Person> getPersonsByIds(List<Integer> personIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Person t ");
		if (null!=personIds&&personIds.size()>0) {
			sb.append("where t.id in(");
			for (Integer id : personIds) {
				sb.append(id).append(",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			sb.append(") order by t.dispIndex");
		}
		Query query;
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
