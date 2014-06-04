package com.ceit.vic.platform.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;

public interface LogService {

	@Transactional
	Map<String, Object> getLogs(int typeId, String content, String personName, String ip,
			Date beginTime, Date endTime, int pageIndex, int pageSize,
			String[] sortNames, String[] sortOrders);

	@Transactional
	void delete(List<Integer> ids);

	@Transactional
	List<LogType> getLogTypes();

	@Transactional
	Map<String, Object> getLogs(int personId, int pageIndex, int pageSize);
	
}
