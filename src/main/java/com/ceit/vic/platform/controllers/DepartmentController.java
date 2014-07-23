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
import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DepartmentService;
import com.ceit.vic.platform.service.LogService;


@Controller
public class DepartmentController {
	static Logger logger = Logger.getLogger(DepartmentController.class);
	@Autowired
	DepartmentService departmentService;
	@Autowired
	LogService logService;
	@RequestMapping(value="/departmentManage")
	public ModelAndView departmentManage() {
		return new ModelAndView("departmentManage");
	}
	
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
	public int addDep(Department department,HttpServletRequest request){
		int id = departmentService.addDepartment(department);
		if (id>0) {
			Log log = new Log();
			log.setIp(logService.getRemoteAddress(request));
			Person person = (Person) request.getSession().getAttribute("user");
			log.setPerson(person);
			StringBuffer sb = new StringBuffer();
			sb.append(person.getCode()).append("(ip:")
			.append(log.getIp()).append(")")
			.append("添加了新部门，部门id:")
			.append(id)
			.append(",部门名称:").append(department.getName());
			log.setContent(sb.toString());
			log.setType(new LogType(3));
			logService.addLog(log);
		}
		return id;
	}
	
	@RequestMapping(value="/depManage/remove/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String depRemove(@PathVariable int depId,HttpServletRequest request){
		String result = departmentService.remove(depId);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person = (Person) request.getSession().getAttribute("user");
		log.setPerson(person);
		StringBuffer sb = new StringBuffer();
		sb.append(person.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("删除部门，部门id:")
		.append(depId);
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return result;
	}
	
	@RequestMapping(value="/depUpdate",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String depUpdate(Department department,HttpServletRequest request){
		departmentService.update(department);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person = (Person) request.getSession().getAttribute("user");
		log.setPerson(person);
		StringBuffer sb = new StringBuffer();
		sb.append(person.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("编辑了部门信息，部门id:")
		.append(department.getId());
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return "编辑成功！";
	}
	
	@RequestMapping(value="/depManage/up/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String up(@PathVariable int depId,HttpServletRequest request){
		departmentService.up(depId);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person = (Person) request.getSession().getAttribute("user");
		log.setPerson(person);
		StringBuffer sb = new StringBuffer();
		sb.append(person.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("上调了部门显示位置，部门id:")
		.append(depId);
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return "上调成功！";
	}
	
	@RequestMapping(value="/depManage/down/{depId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String down(@PathVariable int depId,HttpServletRequest request){
		departmentService.down(depId);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person = (Person) request.getSession().getAttribute("user");
		log.setPerson(person);
		StringBuffer sb = new StringBuffer();
		sb.append(person.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("下调了部门显示位置，部门id:")
		.append(depId);
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return "下调成功！";
	}
}
