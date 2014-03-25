package com.ceit.vic.platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.ResourcesService;

@Controller
public class ModuleController {
	@Autowired
	ResourcesService resourcesService;
	
	@RequestMapping("/moduleChild/{parentId}")
	@ResponseBody
	public List<ZTreeNode> moduleChild(@PathVariable int parentId) throws Exception{
		//改写下面方法使查询结果中包含初始父节点
		List<ZTreeNode> childList = resourcesService.getResourcesTreeById(parentId);
		return childList;
	}
	
	
	@RequestMapping("/moduleIdPass/{moduleId}")
	public ModelAndView moduleIdPass(@PathVariable int moduleId){
		ModelAndView mav = new ModelAndView("westDiv");
		//List<ZTreeNode> nodeList=resourcesService.getResourcesTreeByParentId(id);
		//mav.addObject("nodeList",nodeList);
		mav.addObject("moduleId",moduleId);
		return mav;
	}
}
