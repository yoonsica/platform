package com.ceit.vic.platform.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.service.PersonService;

@Controller
public class PersonController {
	static Logger logger = Logger.getLogger(PersonController.class);
	@Autowired
	PersonService personService;
	@RequestMapping(value="/personByDepId/{depId}")
	public ModelAndView personByDepId(@PathVariable String depId){
		logger.debug("depId:"+depId);//暂时用来跳转到person.jsp，然后ajax获得json数据
		ModelAndView mav = new ModelAndView("person");
		mav.addObject("depId",depId);
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
}
