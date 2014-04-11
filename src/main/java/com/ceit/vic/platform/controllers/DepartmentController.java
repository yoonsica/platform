package com.ceit.vic.platform.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.ModuleInfoDTO;
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
		//List<ZTreeNode> nodeList = departmentService.getDepartmentTree();
		List<ZTreeNode> nodeList = departmentService.getDepartmentTreeById(0);
		return nodeList;
	}
	
	@RequestMapping(value="/department/{depId}")
	public String person(@PathVariable String depId){
		logger.debug("depId:"+depId);
		return "person";
	}
	
	@RequestMapping("/depManage/toAddDepartment/{parentId}")
	public ModelAndView toAddDepartment(@PathVariable int parentId,HttpServletRequest request){
		System.out.println(parentId);
		ModelAndView mav = new ModelAndView("addDep");
		Department department = departmentService.getDepartmentById(parentId);
		mav.addObject("parentId",parentId);
		mav.addObject("parentName",department.getName());
		return mav;
	}
	
	@RequestMapping("/depManage/addDep")
	@ResponseBody
	public int addDep(Department department){
		return departmentService.addDepartment(department);
	}
}
