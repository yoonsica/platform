package com.ceit.vic.platform.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {
	private void aaa() {
		String dir = "D:/Workspaces/MyEclipse 10/platform/src/main/webapp/static/easyui/themes/icon.css";
		String iconDir = "D:/Workspaces/MyEclipse 10/platform/src/main/webapp/static/images/icons";
		List<String> iconNameList = FileTool.getFileNamesByDir(iconDir);
		StringBuffer sb = new StringBuffer();
		for (String iconName : iconNameList) {
			String name = iconName.split(".")[0];
			sb.append(".icon-").append(name)
			.append("{\n\t background:url('icons/").append(iconName)
			.append("') no-repeat;\n}\n");
		}
		File cssFile = new File(dir);
		FileWriter writer;
		try {
			writer = new FileWriter(cssFile, true);
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("(((((");
		new WriteToFile().aaa();
	}

}
