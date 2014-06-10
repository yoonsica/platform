package com.ceit.vic.platform.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.impl.JsonParserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONParser;
import com.ceit.vic.platform.dao.LogDao;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DepartmentService;
import com.ceit.vic.platform.service.LogService;
import com.ceit.vic.platform.service.PersonService;
import com.ceit.vic.platform.service.ResourcesService;
import com.ceit.vic.platform.service.RoleService;
import com.ceit.vic.platform.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	ResourcesService resourcesService;
	
	@Autowired
	LogService logService;
	
	@RequestMapping("/SiteSearch/showSearchResults")
	@ResponseBody
	public Map<String, Object> showSearchResults(
			@RequestParam("searchTypeId") int searchTypeId,
			@RequestParam("searchText") String searchText, 
			@RequestParam("pageIndex") int pageIndex, 
			@RequestParam("pageSize") int pageSize) throws Exception {
		return searchService.getSearchResults(searchTypeId, searchText, pageIndex, pageSize);
	}
	
	@RequestMapping("/SiteSearch/showDepartmentInfo")
	@ResponseBody
	public ModelAndView showDepartmentInfo(@RequestParam("id") int id) throws Exception {
		ModelAndView mav = new ModelAndView("siteSearch/departmentInfo");
		mav.addObject("departmentId", id);
		return mav;
	}
	
	@RequestMapping("/SiteSearch/showRoleInfo")
	@ResponseBody
	public ModelAndView showRoleInfo(@RequestParam("id") int id) throws Exception {
		ModelAndView mav = new ModelAndView("siteSearch/roleInfo");
		mav.addObject("roleId", id);
		return mav;
	}
	
	@RequestMapping("/SiteSearch/showResourceInfo")
	@ResponseBody
	public ModelAndView showResourceInfo(@RequestParam("id") int id) throws Exception {
		ModelAndView mav = new ModelAndView("siteSearch/resourceInfo");
		mav.addObject("resourceId", id);
		return mav;
	}
	
	@RequestMapping("/SiteSearch/showPersonInfo")
	@ResponseBody
	public ModelAndView showPersonInfo(@RequestParam("id") int personId) throws Exception {
		ModelAndView mav = new ModelAndView("siteSearch/personInfo");
		Person person = personService.getPersonById(personId);
		PersonDTO dto = new PersonDTO();
		if (person.getSex()==null) {
			dto.setSex("未知");
		}else {
			dto.setSex(person.getSex().equals("1")?"男":"女");
		}
		dto.setId(person.getId());
		dto.setName(person.getName());
		dto.setCode(person.getCode());
		dto.setMemo(person.getMemo());
		dto.setState(person.getState().equals("0")?"启用":"停用");
		
		mav.addObject("person", dto);
		mav.addObject("department", departmentService.getDepartmentByPersonId(personId));
		return mav;
	}
	
	
	
	@RequestMapping("/SiteSearch/getDepartmentTree")
	@ResponseBody
	public List<ZTreeNode> getDepartmentTree() throws Exception {
		return departmentService.getDepartmentTreeById(0);
	}
	
	@RequestMapping("/SiteSearch/getRoleTree")
	@ResponseBody
	public List<ZTreeNode> getRoleTree() throws Exception {
		return roleService.getRoleTree();
	}

	@RequestMapping("/SiteSearch/getResourceTree")
	@ResponseBody
	public List<ZTreeNode> getResourceTree() throws Exception {
		return resourcesService.getAllResources(true);
	}
	
	
	
	
	
	
	
	@RequestMapping("/SiteSearch/getPersons")
	@ResponseBody
	public Map<String,Object> getPersons(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("page") int pageIndex, @RequestParam("rows") int pageSize){
		Map<String,Object> map = new HashMap<String, Object>();
		if(type.equals("department")) {
			map.put("total", personService.getTotalPersonsByDepId(id));
			map.put("rows", personService.getPersonsByDepId(id, pageIndex, pageSize));
		} else if(type.equals("role")) {
			map.put("total", roleService.getPersonsAmountByRoleId(id));
			map.put("rows", roleService.getPersonsByRoleId(id, pageIndex, pageSize));
		} else if(type.equals("resource")) {
			map.put("total", resourcesService.getAuthTotal(id, 1));
			map.put("rows", resourcesService.getPersonAuthList(id, pageIndex, pageSize));
		}
		
		return map;
	}
	
	@RequestMapping("/SiteSearch/getRoles")
	@ResponseBody
	public Map<String,Object> getRoles(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("page") int pageIndex, @RequestParam("rows") int pageSize) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if(type.equals("person")) {
			map.put("total", roleService.getRoleCountByPersonId(id));
			map.put("rows", roleService.getRolesByPersonId(id, pageIndex, pageSize));
		} else if(type.equals("resource")) {
			map.put("total", roleService.getRoleCountByResourceId(id));
			map.put("rows", roleService.getRolesByResourceId(id, pageIndex, pageSize));
		}
		
		return map;
	}
	
	@RequestMapping("/SiteSearch/getResources")
	@ResponseBody
	public Map<String,Object> getResources(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("page") int pageIndex, @RequestParam("rows") int pageSize) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		int accessType = -1;
		if(type.equals("role")) {
			accessType = 0;
		} else if(type.equals("person")) {
			accessType = 1;
		} else if(type.equals("department")) {
			accessType = 2;
		}
		map.put("total", resourcesService.getResourceCount(id, accessType));
		map.put("rows", resourcesService.getResources(id, accessType, pageIndex, pageSize));
		return map;
	}
	
	@RequestMapping("/SiteSearch/getDepartments")
	@ResponseBody
	public Map<String,Object> getDepartments(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("page") int pageIndex, @RequestParam("rows") int pageSize){
		Map<String,Object> map = new HashMap<String, Object>();
		if(type.equals("resource")) {
			map.put("total", departmentService.getDepartmentCountByResourceId(id));
			map.put("rows", departmentService.getDepartmentsByResourceId(id, pageIndex, pageSize));
		}
		
		return map;
	}
	
	@RequestMapping("/SiteSearch/getLogs")
	@ResponseBody
	public Map<String,Object> getLogs(@RequestParam("id") int id, @RequestParam("page") int pageIndex, @RequestParam("rows") int pageSize) throws Exception {
		return logService.getLogs(id, pageIndex, pageSize);
	}
	
}