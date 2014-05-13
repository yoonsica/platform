package com.ceit.vic.platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.models.Dictionary;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DictionaryService;

@Controller
public class DictionaryController {
	
	@Autowired
	DictionaryService dictionaryService;
	
	@RequestMapping("/dictionaryManage/show")
	@ResponseBody
	public List<ZTreeNode> showDictionaryTree() throws Exception {
		List<ZTreeNode> dictionaryTree = dictionaryService.generateDictionaryTree();
		return dictionaryTree;
	}
	
	@RequestMapping("/dictionaryManage/add")
	@ResponseBody
	public void addDictionaryElement(@RequestParam("parentId") int parentId, @RequestParam("isParent") boolean isParent, @RequestParam("name") String name, @RequestParam("value") String value) throws Exception {
		dictionaryService.addDictionaryElement(parentId, isParent, name, value);
	}
	
	@RequestMapping("/dictionaryManage/update")
	@ResponseBody
	public void updateDictionaryElement(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("value") String value) throws Exception {
		dictionaryService.updateDictionaryElement(id, name, value);
	}
	
	@RequestMapping("/dictionaryManage/delete")
	@ResponseBody
	public void deleteDictionaryElement(@RequestParam("id") int id) throws Exception {
		dictionaryService.deleteDictionaryElement(id);
	}
	
	@RequestMapping("/dictionaryManage/up")
	@ResponseBody
	public void upDictionaryElement(@RequestParam("currentId") int currentId, @RequestParam("prevId") int prevId) throws Exception {
		dictionaryService.upDictionaryElement(currentId, prevId);
	}
	
	@RequestMapping("/dictionaryManage/down")
	@ResponseBody
	public void downDictionaryElement(@RequestParam("currentId") int currentId, @RequestParam("nextId") int nextId) throws Exception {
		dictionaryService.downDictionaryElement(currentId, nextId);
	}
}
