package com.ceit.vic.platform.dao;


public interface IDPROVIDERDao {
	/**
	 * 从种子表中获得当前id
	 * @param tableName 表名
	 * @return
	 */
	public int getCurrentId(String tableName);
	/**
	 * 指定表的当前id+1
	 * @param tableName
	 */
	public void add(String tableName);
}
