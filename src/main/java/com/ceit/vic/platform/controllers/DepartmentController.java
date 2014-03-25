package com.ceit.vic.platform.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DepartmentService;


@Controller
public class DepartmentController {
	static Logger logger = Logger.getLogger(DepartmentController.class);
	@Autowired
	DepartmentService departmentService;
	
	
	@RequestMapping(value="/department")
	@ResponseBody
	public List<ZTreeNode> init() {
		logger.debug("init");
		List<ZTreeNode> nodeList = departmentService.getDepartmentTree();
		return nodeList;
	}
	
	@RequestMapping(value="/department/{depId}")
	public String person(@PathVariable String depId){
		logger.debug("depId:"+depId);
		return "person";
	}
}
