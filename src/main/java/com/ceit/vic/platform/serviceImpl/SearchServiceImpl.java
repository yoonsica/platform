package com.ceit.vic.platform.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.SearchDao;
import com.ceit.vic.platform.models.SearchResultDTO;
import com.ceit.vic.platform.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchDao searchDao;
	
	@Override
	public Map<String, Object> getSearchResults(int searchTypeId,
			String searchText, int pageIndex, int pageSize) {
		return searchDao.findResults(searchTypeId, searchText, pageIndex, pageSize);
	}

}
