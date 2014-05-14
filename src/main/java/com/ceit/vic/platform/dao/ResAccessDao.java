package com.ceit.vic.platform.dao;

import java.util.List;


public interface ResAccessDao {
	/**
	 * 通过资源id找到已授权的id列表
	 * @param resId
	 * @param accessType
	 * @param maxResults 
	 * @param firstResult 
	 * @return
	 */
	public List<Integer> getAccessIdsByResId(int resId,int accessType, int firstResult, int maxResults);
}
