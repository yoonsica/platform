package com.ceit.vic.platform.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.RoleDTO;
import com.ceit.vic.platform.service.DepartmentService;
import com.ceit.vic.platform.service.PersonService;
import com.ceit.vic.platform.service.RoleService;

@Controller
public class PersonController {
	static Logger logger = Logger.getLogger(PersonController.class);
	@Autowired
	PersonService personService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	RoleService roleService;
	
	
	@RequestMapping(value="/personByDepId/{depId}")
	public ModelAndView personByDepId(@PathVariable String depId){
		logger.debug("depId:"+depId);//暂时用来跳转到person.jsp，然后ajax获得json数据
		ModelAndView mav = new ModelAndView("person");
		mav.addObject("depId",depId);
		return mav;
	}
	
	@RequestMapping(value="/personByDepIdToAuth/{depId}/{resId}")
	public ModelAndView personByDepIdToAuth(@PathVariable String depId,@PathVariable String resId){
		logger.debug("depId:"+depId);//暂时用来跳转到person.jsp，然后ajax获得json数据
		ModelAndView mav = new ModelAndView("personToSelect");
		mav.addObject("depId",depId);
		mav.addObject("resId",resId);
		return mav;
	}
	@RequestMapping(value="/personByDepIdRoleId/{depId}/{roleId}")
	public ModelAndView personByDepIdRoleId(@PathVariable int depId,@PathVariable int roleId){
		logger.debug("depId:"+depId);//暂时用来跳转到person.jsp，然后ajax获得json数据
		ModelAndView mav = new ModelAndView("addPersonRole");
		mav.addObject("depId",depId);
		mav.addObject("roleId",roleId);
		return mav;
	}
	
	@RequestMapping("/persons/{depId}")
	@ResponseBody
	public Map<String,Object> persons(@PathVariable int depId,int page,int rows){
		logger.debug("depId:"+depId);
		System.out.println(page+"**********************");
		logger.debug("rows:"+rows);
		int total = personService.getTotalPersonsByDepId(depId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total",total);
		map.put("rows", personService.getPersonsByDepId(depId,page,rows));
		//给total赋值
		return map;
	}
	
	
	@RequestMapping("/toAddPerson/{depId}")
	public ModelAndView toAddPerson(@PathVariable int depId){
		ModelAndView mav = new ModelAndView("addPerson");
		Department department = departmentService.getDepartmentById(depId);
		mav.addObject("department",department);
		return mav;
	}
	
	@RequestMapping("/addPerson")
	@ResponseBody
	public String addPerson(HttpServletRequest request){
		int depId = Integer.valueOf(request.getParameter("depId"));
		Person person = new Person();
		person.setCode(request.getParameter("code"));
		person.setName(request.getParameter("name"));
		person.setSex(request.getParameter("sex"));
		person.setState(request.getParameter("state"));
		person.setMemo(request.getParameter("memo"));
		personService.add(person,-1,depId);
		return "添加成功！";
	}
	
	@RequestMapping("/upPerson/{depId}/{personId}")
	@ResponseBody
	public String upPerson(@PathVariable int depId,@PathVariable int personId){
		return personService.up(depId,personId);
	}
	
	@RequestMapping("/downPerson/{depId}/{personId}")
	@ResponseBody
	public String downPerson(@PathVariable int depId,@PathVariable int personId){
		return personService.down(depId,personId);
	}
	
	@RequestMapping("/toEditPerson/{depId}/{personId}")
	public ModelAndView toEditPerson(@PathVariable int depId,@PathVariable int personId){
		ModelAndView mav = new ModelAndView("editPerson");
		Department department = departmentService.getDepartmentById(depId);
		Person person = personService.getPersonById(personId);
		mav.addObject("department",department);
		mav.addObject("person",person);
		return mav;
	}
	
	@RequestMapping("/editPerson")
	@ResponseBody
	public String editPerson(HttpServletRequest request){
		int depId = Integer.valueOf(request.getParameter("depId"));
		Person person = new Person();
		person.setId(Integer.valueOf(request.getParameter("personId")));
		person.setCode(request.getParameter("code"));
		person.setName(request.getParameter("name"));
		person.setSex(request.getParameter("sex"));
		person.setState(request.getParameter("state"));
		person.setMemo(request.getParameter("memo"));
		personService.update(person);
		personService.updateDepartment(person.getId(),depId);
		return "编辑成功！";
	}
	
	@RequestMapping("/deletePerson/{depId}")
	@ResponseBody
	public String deletePerson(@PathVariable int depId,int[] idArray){
		personService.delete(idArray);
		personService.deleteDepPerson(depId,idArray);
		personService.deletePersonRole(idArray);
		return "删除成功！";
	}
	
	@RequestMapping("/person/toAuthorizeRole/{personId}")
	public ModelAndView toAuthorizeRole(@PathVariable int personId){
		Person person = personService.getPersonById(personId);
		ModelAndView mav = new ModelAndView("toAuthorizeRole");
		mav.addObject("person",person);
		return mav;
	}

	@RequestMapping(value="/roleListByPersonId/{personId}")
	@ResponseBody
	public List<RoleDTO> roleListByPersonId(@PathVariable int personId){
		List<Role> roleList = roleService.getRolesByPersonId(personId);
		List<RoleDTO> dtos = new ArrayList<RoleDTO>();
		for (Role role : roleList) {
			RoleDTO dto = new RoleDTO(role.getId(), role.getName(), role.getMemo());
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	@RequestMapping(value="/authorizeRole",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String authorizeRole(int[] roleIds,int personId){
		System.out.println(roleIds);
		roleService.addPersonRole(roleIds, personId);
		return "添加成功！";
	}
	
	@RequestMapping(value="/resetPassword",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String authorizeRole(int[] idArray){
		personService.resetPassword(idArray);
		return "密码已经重置为用户名";
	}
}
