package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.ResAccess;


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

	public void add(ResAccess resAccess);

	public void deleteResAccess(int[] idArray, int resId, int accessType);

	public int getAccessIdTotalByResId(int resId,int accessType);
}
