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
	

	@RequestMapping("/moduleManage")
	@ResponseBody
	public List<ZTreeNode> moduleManage() throws Exception{
		List<ZTreeNode> childList = resourcesService.getAllResources(false);
		return childList;
	}
	
	@RequestMapping("/moduleWithBtnLinkManage")
	@ResponseBody
	public List<ZTreeNode> moduleWithBtnLinkManage() throws Exception{
		List<ZTreeNode> childList = resourcesService.getAllResources(true);
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
	
	@RequestMapping("/folderInfo/{moduleId}")
	public ModelAndView folderInfo(@PathVariable int moduleId,HttpServletRequest request){
		String dir = request.getSession().getServletContext().getRealPath("/static/images/icons");
		List<String> iconList = FileTool.getFileNamesByDir(dir);
		ModelAndView mav = new ModelAndView("folderInfo");
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
	

	@RequestMapping("/toAddButtonLink/{moduleId}")
	public ModelAndView toAddButtonLink(@PathVariable int moduleId){
		ModelAndView mav = new ModelAndView("addButtonLink");
		mav.addObject("resId",moduleId);
		return mav;
	}
	
	@RequestMapping("/addButtonLink")
	public ModelAndView addButtonLink(ModuleInfoDTO dto){
		ModelAndView mav = new ModelAndView("buttonLink");
		resourcesService.addResource(dto, false);
		return mav;
	}
	
	@RequestMapping("/toResAuth/{moduleId}")
	public ModelAndView toAddFunction(@PathVariable int moduleId){
		ModelAndView mav = new ModelAndView("resAuth");
		mav.addObject("resId",moduleId);
		return mav;
	}
	
	@RequestMapping("/resAuth/{accessType}/{resId}")
	@ResponseBody
	public Map<String,Object> resAuth(@PathVariable int accessType,@PathVariable int resId,int page,int rows){
		Map<String,Object> map = new HashMap<String, Object>();
		if (accessType==2) {
			map.put("rows",resourcesService.getDepAuthList(resId,page,rows));
		}
		
		/*map.put("total",total);
		map.put("rows", personService.getPersonsByDepId(depId,page,rows));*/
		//给total赋值
		return map;
	}
	
	@RequestMapping("/buttonLinkList/{resId}")
	@ResponseBody
	public Map<String,Object> buttonLinkList(@PathVariable int resId,int page,int rows){
		System.out.println(resId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows",resourcesService.getButtonLinkList(resId,page,rows));
		map.put("total",resourcesService.getButtonLinkListSize(resId));
		/*map.put("total",total);
		map.put("rows", personService.getPersonsByDepId(depId,page,rows));*/
		//给total赋值
		return map;
	}
	
	@RequestMapping("/deleteButtonLink")
	@ResponseBody
	public String deleteButtonLink(int[] idArray){
		resourcesService.remove(idArray);
		return "删除成功！";
	}
	
	@RequestMapping("/toAddDepAuth/{moduleId}")
	public ModelAndView toAddDepAuth(@PathVariable int moduleId){
		ModelAndView mav = new ModelAndView("toAddDepAuth");
		mav.addObject("resId",moduleId);
		return mav;
	}
	
	@RequestMapping("/addDepAuth")
	@ResponseBody
	public String addDepAuth(int[] depIds,int resId,int accessType){
		resourcesService.addResAccess(depIds,resId,accessType);
		return "删除成功！";
	}
	
	@RequestMapping("/deleteDepAuth/{resId}")
	@ResponseBody
	public String deleteDepAuth(int[] idArray,int resId){
		resourcesService.deleteResAccess(idArray,resId,2);
		return "删除成功！";
	}
}
