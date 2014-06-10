package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.SearchDao;
import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.SearchResultDTO;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SessionFactory sf;
	
	@Override
	public Map<String, Object> findResults(int searchTypeId,
			String searchText, int pageIndex, int pageSize) {
		StringBuffer sqlCondition = new StringBuffer();
		sqlCondition.append("from ");
		
		switch (searchTypeId) {
		case 1:
			sqlCondition.append("Department ");
			break;
		case 2:
			sqlCondition.append("Role ");
			break;
		case 3:
			sqlCondition.append("Resources ");
			break;
		case 4:
			sqlCondition.append("Person ");
			break;
		default:
			break;
		}
		sqlCondition.append("where id > 0 and name like '%" + searchText + "%'");
		
		Map<String, Object> searchResult = new HashMap<String, Object>();
		try {
			Query query = sf.getCurrentSession().createQuery("select id, name " + sqlCondition.toString());
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Object[]> queryObjs = query.list();
			List<SearchResultDTO> results = new ArrayList<SearchResultDTO>();
			
			for(Object[] result : queryObjs) { 
				results.add(new SearchResultDTO(Integer.parseInt(String.valueOf(result[0])), String.valueOf(result[1]), searchTypeId));
			}
			
			query = sf.getCurrentSession().createQuery("select count(*) " + sqlCondition.toString());
			int resultCount = Integer.parseInt(String.valueOf(query.uniqueResult()));
			
			searchResult.put("rows", results);
			searchResult.put("total", resultCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchResult;
	}

}
