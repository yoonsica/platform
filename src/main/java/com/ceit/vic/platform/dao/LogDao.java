package com.ceit.vic.platform.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;

public interface LogDao {
	void add(Log log);
	void delete(List<Integer> ids);
	Map<String, Object> find(int typeId, String content, String personName, String ip,
			Date beginTime, Date endTime, int pageIndex, int pageSize,
			String[] sortNames, String[] sortOrders);
	Map<String, Object> find(int personId, int pageIndex, int pageSize);
	List<LogType> findAllTypes();
	
}
