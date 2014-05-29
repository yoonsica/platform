package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.Dep_PersonDao;
import com.ceit.vic.platform.models.Dep_Person;
@Repository
public class Dep_PersonDaoImpl implements Dep_PersonDao  {

	@Autowired
	SessionFactory sf;
	@Override
	public void add(Dep_Person dep_Person) {
		sf.getCurrentSession().save(dep_Person);
		
	}

	@Override
	public void delete(int id) {
		Dep_Person dep_Person = getById(id);
		sf.getCurrentSession().delete(dep_Person);
	}

	@Override
	public Dep_Person getById(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Dep_Person t where t.id = "+id);
			return (Dep_Person) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void update(Dep_Person dep_Person) {
		sf.getCurrentSession().update(dep_Person);
		
	}

	@Override
	public void deletePerson(int depId, int[] idArray) {
		if (idArray!=null) {
			StringBuffer sb = new StringBuffer("delete Dep_Person t where t.personId in(");
			for (int i = 0; i < idArray.length-1; i++) {
				sb.append(idArray[i]).append(",");
			}
			sb.append(idArray[idArray.length-1]).append(") and t.depId=").append(depId);
			sf.getCurrentSession().createQuery(sb.toString()).executeUpdate();
		}
	}

	@Override
	public List<Dep_Person> getByPersonId(int id,boolean mainDep) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Dep_Person t where t.personId = "+id+" and t.mainDep=1");
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
