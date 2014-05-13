package com.ceit.vic.platform.dao;

import java.util.Date;
import java.util.List;

import com.ceit.vic.platform.models.Log;

public interface LogDao {
	void add(Log log);
	void delete(List<Integer> ids);
	List<Log> find(int typeId, String content, String username, String ip, Date beginTime, Date endTime);
}
