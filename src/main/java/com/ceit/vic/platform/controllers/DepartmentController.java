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
	
	@RequestMapping(value="/depInfo/{depId}")
	public ModelAndView person(@PathVariable int depId){
		logger.debug("depId:"+depId);
		ModelAndView mav = new ModelAndView("depEdit");
		mav.addObject("department",departmentService.getDepartmentById(depId));
		return mav;
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
	
	@RequestMapping(value="/depManage/remove/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String depRemove(@PathVariable int depId){
		departmentService.remove(depId);
		return "删除成功!";
	}
	
	@RequestMapping("/depUpdate")
	@ResponseBody
	public String depUpdate(Department department){
		departmentService.update(department);
		return "编辑成功！";
	}
	
	@RequestMapping(value="/depManage/up/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String up(@PathVariable int depId){
		System.out.println(depId);
		departmentService.up(depId);
		return "上调成功！";
	}
	
	@RequestMapping(value="/depManage/down/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String down(@PathVariable int depId){
		System.out.println(depId);
		departmentService.down(depId);
		return "下调成功！";
	}
}
