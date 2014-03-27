package com.ceit.vic.platform.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileTool {
	/**
	 * 获取文件夹内所有文件名
	 * @param dir 服务器文件夹地址
	 * @return 所有文件夹名集合
	 */
	public static List<String> getFileNamesByDir(String dir){
		List<String> list = new ArrayList<String>();
		File file = new File(dir);
		File[] files = file.listFiles();
		for (File file2 : files) {
			list.add(file2.getName());
		}
		return list;
	}
}
