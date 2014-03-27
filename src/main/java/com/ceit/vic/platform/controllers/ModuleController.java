package com.ceit.vic.platform.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.ModuleInfoDTO;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.ResourcesService;
import com.ceit.vic.platform.tools.FileTool;

@Controller
public class ModuleController {
	@Autowired
	ResourcesService resourcesService;
	
	@RequestMapping("/moduleChild/{parentId}")
	@ResponseBody
	public List<ZTreeNode> moduleChild(@PathVariable int parentId) throws Exception{
		List<ZTreeNode> childList = resourcesService.getResourcesTreeById(parentId,false);
		return childList;
	}
	

	@RequestMapping("/moduleManage/{parentId}")
	@ResponseBody
	public Map<String, Object> moduleManage(@PathVariable int parentId) throws Exception{
		List<ZTreeNode> childList = resourcesService.getResourcesTreeById(parentId,true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("childList", childList);
		map.put("count", 10);
		return map;
	}
	@RequestMapping("/moduleIdPass/{moduleId}")
	public ModelAndView moduleIdPass(@PathVariable int moduleId){
		ModelAndView mav = new ModelAndView("westDiv");
		//List<ZTreeNode> nodeList=resourcesService.getResourcesTreeByParentId(id);
		//mav.addObject("nodeList",nodeList);
		mav.addObject("moduleId",moduleId);
		return mav;
	}
	
	
	@RequestMapping("/moduleInfo/{moduleId}")
	public ModelAndView moduleInfo(@PathVariable int moduleId,HttpServletRequest request){
		String dir = request.getSession().getServletContext().getRealPath("/static/images/icons");
		List<String> iconList = FileTool.getFileNamesByDir(dir);
		ModelAndView mav = new ModelAndView("moduleInfo");
		ModuleInfoDTO moduleInfo = resourcesService.getModuleInfoById(moduleId);
		mav.addObject("moduleInfo",moduleInfo);
		mav.addObject("iconList",iconList);
		return mav;
	}
	
	@RequestMapping("/moduleUpdate")
	@ResponseBody
	public String moduleUpdate(ModuleInfoDTO moduleInfo){
		System.out.println(moduleInfo);
		resourcesService.updateResources(moduleInfo);
		return "OK";
	}
}
