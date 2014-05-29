package com.ceit.vic.platform.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.ResAccessDao;
import com.ceit.vic.platform.models.ResAccess;
import com.ceit.vic.platform.service.ResAccessService;
@Service
public class ResAccessServiceImpl implements ResAccessService {
	@Autowired
	ResAccessDao resAccessDao;

	@Override
	public List<ResAccess> getByParamMap(Map<String, Object> paraMap) {
		return resAccessDao.getByParamMap(paraMap);
	}

}
