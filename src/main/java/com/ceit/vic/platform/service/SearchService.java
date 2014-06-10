package com.ceit.vic.platform.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.SearchResultDTO;

public interface SearchService {

	@Transactional
	Map<String, Object> getSearchResults(int searchTypeId,
			String searchText, int pageIndex, int pageSize);

}
