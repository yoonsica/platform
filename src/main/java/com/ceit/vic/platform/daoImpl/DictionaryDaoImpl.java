package com.ceit.vic.platform.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.DictionaryDao;
import com.ceit.vic.platform.models.Dictionary;

@Repository
public class DictionaryDaoImpl implements DictionaryDao {

	@Autowired
	SessionFactory sf;
	
	@Override
	public Dictionary findById(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Dictionary d where d.id = '" + id + "'");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return (Dictionary) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> findAll() {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Dictionary d order by d.dispIndex");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return query.list();
	}
	
	@Override
	public void add(Dictionary dictionary) {
		sf.getCurrentSession().save(dictionary);
	}

	@Override
	public void update(Dictionary dictionary) {
		sf.getCurrentSession().update(dictionary);
	}

	@Override
	public void delete(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createSQLQuery("delete Dictionary where id in( " + 
				"select id from Dictionary " + 
				"start with id = :id " + 
				"connect by prior id = parent_id)");
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void up(int currentId, int prevId) {
		Dictionary currentDictionary = findById(currentId);
		Dictionary prevDictionary = findById(prevId);
		int tmpId = currentDictionary.getDispIndex();
		currentDictionary.setDispIndex(prevDictionary.getDispIndex());
		prevDictionary.setDispIndex(tmpId);
		
		//sf.getCurrentSession().update(currentDictionary);
		//sf.getCurrentSession().update(prevDictionary);
	}

	@Override
	public void down(int currentId, int nextId) {
		Dictionary currentDictionary = findById(currentId);
		Dictionary nextDictionary = findById(nextId);
		int tmpId = currentDictionary.getDispIndex();
		currentDictionary.setDispIndex(nextDictionary.getDispIndex());
		nextDictionary.setDispIndex(tmpId);
		
		//sf.getCurrentSession().update(currentDictionary);
		//sf.getCurrentSession().update(nextDictionary);
	}

}
