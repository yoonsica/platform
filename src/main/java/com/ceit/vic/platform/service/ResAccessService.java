package com.ceit.vic.platform.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.ResAccess;


public interface ResAccessService {
	@Transactional
	public List<ResAccess> getByParamMap(Map<String, Object> paraMap);
}
