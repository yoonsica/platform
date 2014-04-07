package com.ceit.vic.platform.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.ModuleInfoDTO;
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
		List<ZTreeNode> childList = resourcesService.getResourcesTreeById(parentId,false,false);
		return childList;
	}
	

	@RequestMapping("/moduleManage/{parentId}")
	@ResponseBody
	public Map<String, Object> moduleManage(@PathVariable int parentId) throws Exception{
		List<ZTreeNode> childList = resourcesService.getResourcesTreeById(parentId,true,true);
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
		return "编辑成功！";
	}
	
	@RequestMapping(value="/moduleManage/up/{moduleId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String up(@PathVariable int moduleId){
		System.out.println(moduleId);
		resourcesService.up(moduleId);
		return "上调成功！";
	}
	
	@RequestMapping(value="/moduleManage/down/{moduleId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String down(@PathVariable int moduleId){
		System.out.println(moduleId);
		resourcesService.down(moduleId);
		return "下调成功！";
	}
	@RequestMapping("/moduleManage/toAddFolder/{parentId}")
	public ModelAndView toAddFolder(@PathVariable int parentId,HttpServletRequest request){
		System.out.println(parentId);
		
		ModelAndView mav = new ModelAndView("moduleAddFolder");
		String dir = request.getSession().getServletContext().getRealPath("/static/images/icons");
		List<String> iconList = FileTool.getFileNamesByDir(dir);
		mav.addObject("parent",parentId);
		mav.addObject("iconList",iconList);
		return mav;
	}
	
	@RequestMapping("/moduleManage/toAddFunction/{parentId}")
	public ModelAndView toAddFunction(@PathVariable int parentId,HttpServletRequest request){
		System.out.println(parentId);
		ModelAndView mav = new ModelAndView("moduleAddFunction");
		String dir = request.getSession().getServletContext().getRealPath("/static/images/icons");
		List<String> iconList = FileTool.getFileNamesByDir(dir);
		mav.addObject("parent",parentId);
		mav.addObject("iconList",iconList);
		return mav;
	}
	
	@RequestMapping("/moduleManage/AddFolder")
	@ResponseBody
	public int moduleAddFolder(ModuleInfoDTO moduleInfo){
		System.out.println(moduleInfo);
		return resourcesService.addResource(moduleInfo,true);
	}
	
	@RequestMapping("/moduleManage/AddFunction")
	@ResponseBody
	public int moduleAddFunction(ModuleInfoDTO moduleInfo){
		System.out.println(moduleInfo);
		return resourcesService.addResource(moduleInfo,false);
	}
	
	
	@RequestMapping(value="/moduleManage/remove/{moduleId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String moduleRemove(@PathVariable int moduleId){
		resourcesService.remove(moduleId);
		return "删除成功!";
	}
}
