package com.ceit.vic.platform.dao;

import java.util.Map;

import com.ceit.vic.platform.models.SearchResultDTO;

public interface SearchDao {

	Map<String, Object> findResults(int searchTypeId, String searchText, int pageIndex, int pageSize);

}
