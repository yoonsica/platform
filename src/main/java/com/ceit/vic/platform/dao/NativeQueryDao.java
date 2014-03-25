package com.ceit.vic.platform.dao;

public interface NativeQueryDao {
	/**
	 * 简单的本地查询
	 * @param sql
	 * @return
	 */
	public Object nativeQuery(String sql);
	/**
	 * 本地分页查询
	 * @param pageNo 当前页面号
	 * @param pageSize 每页的记录数
	 * @param sqlStr
	 * @return
	 */
	public Object nativeQueryByPage(int pageNo,int pageSize,String sqlStr);
}
