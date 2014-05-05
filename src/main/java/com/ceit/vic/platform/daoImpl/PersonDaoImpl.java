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

	@Override
	public Person getPersonsById(int id) {
		Query query;
		try {
			query = sf.getCurrentSession().createQuery("from Person t where t.id="+id);
			return (Person) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public Person getPersonToDown(int depId,int dispIndex) {
		Query query;
		List<String> personIdList;
		try {
			query = sf.getCurrentSession().createQuery("select t.personId from Dep_Person t where t.depId=");
			personIdList = query.list();
			StringBuffer sb = new StringBuffer("from Person t where t.id in(");
			for (String id : personIdList) {
				sb.append(id).append(",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			sb.append(") and t.dispIndex<").append(dispIndex)
			.append(" order by t.dispIndex desc");
			query = sf.getCurrentSession().createQuery(sb.toString());
			if (query.list()!=null&&query.list().size()>0) {
				return (Person) query.list().get(0);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Person getPersonToUp(int depId, int dispIndex) {
		Query query;
		List<String> personIdList;
		try {
			query = sf.getCurrentSession().createQuery("select t.personId from Dep_Person t where t.depId=");
			personIdList = query.list();
			StringBuffer sb = new StringBuffer("from Person t where t.id in(");
			for (String id : personIdList) {
				sb.append(id).append(",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			sb.append(") and t.dispIndex>").append(dispIndex)
			.append(" order by t.dispIndex");
			query = sf.getCurrentSession().createQuery(sb.toString());
			if (query.list()!=null&&query.list().size()>0) {
				return (Person) query.list().get(0);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void multDelete(int[] idArray) {
		if (idArray!=null) {
			StringBuffer sb = new StringBuffer("delete Person t where t.id in(");
			for (int i = 0; i < idArray.length-1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length-1]).append(")");
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
	}

}
