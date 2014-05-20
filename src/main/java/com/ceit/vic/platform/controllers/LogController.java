package com.ceit.vic.platform.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.service.LogService;

@Controller
public class LogController {

	@Autowired
	LogService logService;

	@RequestMapping("/logManage/show")
	@ResponseBody
	public Map<String, Object> showLogs(
			@RequestParam("typeId") int typeId,
			@RequestParam("content") String content,
			@RequestParam("personName") String personName,
			@RequestParam("ip") String ip,
			@RequestParam("beginTime") Date beginTime,
			@RequestParam("endTime") Date endTime, 
			@RequestParam("pageIndex") int pageIndex, 
			@RequestParam("pageSize") int pageSize, 
			@RequestParam("sortNames") String sortNames, 
			@RequestParam("sortOrders") String sortOrders) throws Exception {
		Map<String, Object> logResult = logService.getLogs(
				typeId, 
				content, 
				personName, 
				ip, 
				beginTime, 
				endTime, 
				pageIndex, 
				pageSize, 
				sortNames.equals("null") || sortNames.equals("") ? null : sortNames.split(","), 
				sortOrders.equals("null") || sortOrders.equals("") ? null : sortOrders.split(","));
		return logResult;
	}
	
	@RequestMapping("/logManage/delete")
	@ResponseBody
	public void deleteLogs(@RequestParam("ids") List<Integer> ids) throws Exception {
		logService.delete(ids);
	}
	
	@RequestMapping("/logManage/getLogTypes")
	@ResponseBody
	public List<LogType> getLogTypes() throws Exception {
		return logService.getLogTypes();
	}
}
