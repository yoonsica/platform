package com.ceit.vic.platform.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.LogDao;
import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao logDao;
	
	@Autowired
	IDPROVIDERDao idproviderDao;
	
	@Override
	public Map<String, Object> getLogs(int typeId, String content, String personName,
			String ip, Date beginTime, Date endTime, int pageIndex, int pageSize,
			String[] sortNames, String[] sortOrders) {
		return logDao.find(typeId, content, personName, ip, beginTime, endTime, 
				pageIndex, pageSize, sortNames, sortOrders);
	}

	@Override
	public void delete(List<Integer> ids) {
		logDao.delete(ids);
	}

	@Override
	public List<LogType> getLogTypes() {
		List<LogType> logTypes = logDao.findAllTypes();
		logTypes.add(0, new LogType(0, "全部"));
		return logTypes;
	}

}
