package com.ceit.vic.platform.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Role;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.LogService;
import com.ceit.vic.platform.service.RoleService;

@Controller
public class RoleController {
	static Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	RoleService roleService;
	@Autowired
	LogService logService;
	
	@RequestMapping("roleManage")
	@ResponseBody
	public List<ZTreeNode> roleTree(){
		return roleService.getRoleTree();
	}
	
	/**
	 * 跳转到编辑页面
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/roleInfo/{roleId}")
	public ModelAndView roleInfo(@PathVariable int roleId){
		logger.debug("roleId"+roleId);
		ModelAndView mav = new ModelAndView("roleEdit");
		mav.addObject("role",roleService.getRoleById(roleId));
		return mav;
	}
	
	/**
	 * 编辑，更新
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/roleUpdate",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String roleUpdate(Role role,HttpServletRequest request){
		roleService.update(role);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person1 = (Person) request.getSession().getAttribute("user");
		log.setPerson(person1);
		StringBuffer sb = new StringBuffer();
		sb.append(person1.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("编辑了角色信息，角色id:")
		.append(role.getId());
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return "更新成功！";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/roleRemove/{id}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String roleRemove(@PathVariable int id,HttpServletRequest request){
		String result = roleService.delete(id);
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person1 = (Person) request.getSession().getAttribute("user");
		log.setPerson(person1);
		StringBuffer sb = new StringBuffer();
		sb.append(person1.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("删除了角色，角色id:")
		.append(id);
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return result;
	}
	
	/**
	 * 跳转到增加角色页面
	 * @param parentId
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddRole/{parentId}")
	public ModelAndView toAddRole(@PathVariable int parentId,HttpServletRequest request){
		System.out.println(parentId);
		ModelAndView mav = new ModelAndView("addRole");
		Role role = roleService.getRoleById(parentId);
		mav.addObject("parentId",parentId);
		mav.addObject("parentName",role.getName());
		return mav;
	}
	/**
	 * 增加角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	public int addRole(Role role,HttpServletRequest request){
		Log log = new Log();
		log.setIp(logService.getRemoteAddress(request));
		Person person1 = (Person) request.getSession().getAttribute("user");
		log.setPerson(person1);
		StringBuffer sb = new StringBuffer();
		sb.append(person1.getCode()).append("(ip:")
		.append(log.getIp()).append(")")
		.append("增加了角色，角色id:")
		.append(role.getId());
		log.setContent(sb.toString());
		log.setType(new LogType(3));
		logService.addLog(log);
		return roleService.add(role);
	}
	
	@RequestMapping(value="/personByRoleId/{roleId}")
	public ModelAndView personByDepId(@PathVariable String roleId){
		ModelAndView mav = new ModelAndView("personByRole");
		mav.addObject("roleId",roleId);
		return mav;
	}
	
	@RequestMapping(value="/depByRoleId/{roleId}")
	public ModelAndView depByRoleId(@PathVariable String roleId){
		ModelAndView mav = new ModelAndView("depByRole");
		mav.addObject("roleId",roleId);
		return mav;
	}
	
	@RequestMapping("/getDepsByRoleId/{roleId}")
	@ResponseBody
	public Map<String,Object> getDepsByRoleId(@PathVariable int roleId,int page,int rows){
		logger.debug("depId:"+roleId);
		System.out.println(page+"**********************");
		logger.debug("rows:"+rows);
		int total = roleService.getDepsAmountByRoleId(roleId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total",total);
		if (total>0) {
			map.put("rows", roleService.getDepsByRoleId(roleId, page, rows));
		}
		return map;
	}
	
	@RequestMapping("/getPersonsByRoleId/{roleId}")
	@ResponseBody
	public Map<String,Object> persons(@PathVariable int roleId,int page,int rows){
		logger.debug("roleId:"+roleId);
		System.out.println(page+"**********************");
		logger.debug("rows:"+rows);
		int total = roleService.getPersonsAmountByRoleId(roleId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total",total);
		if (total>0) {
			map.put("rows", roleService.getPersonsByRoleId(roleId, page, rows));
		}
		return map;
	}
	/**
	 * 对person取消授权
	 * @return
	 */
	@RequestMapping(value="/cancleRole/{roleId}",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String canclePersonRole(@PathVariable int roleId,String[] idArray){
		System.out.println(idArray);
		roleService.canclePersonRole(roleId, idArray);
		return "取消授权成功！";
	}
	
	@RequestMapping("/toAddPersonRole/{roleId}")
	public ModelAndView toAddPersonRole(@PathVariable int roleId){
		ModelAndView mav = new ModelAndView("toAddPersonRole");
		Role role = roleService.getRoleById(roleId);
		mav.addObject("role",role);
		return mav;
	}
	
	@RequestMapping("/toAddDepRole/{roleId}")
	public ModelAndView toAddDepRole(@PathVariable int roleId){
		ModelAndView mav = new ModelAndView("toAddDepRole");
		Role role = roleService.getRoleById(roleId);
		mav.addObject("role",role);
		return mav;
	}
	
	@RequestMapping(value="/addPersonRole/{roleId}", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String addPersonRole(@PathVariable int roleId,int[] idArray){
		roleService.addPersonRole(roleId,idArray);
		return "添加成功！";
	}
	
	@RequestMapping(value="/addDepRole/{roleId}", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String addDepRole(@PathVariable int roleId,int[] idArray){
		roleService.addDepRole(roleId,idArray);
		return "添加成功！";
	}
	@RequestMapping(value="/toRoleManage")
	public ModelAndView toRoleManage(){
		ModelAndView mv = new ModelAndView("role");
		return mv;
	}
	
	@RequestMapping(value="/toAddDepRole")
	public ModelAndView toAddDepRole(){
		ModelAndView mv = new ModelAndView("toAddDepRole");
		return mv;
	}
}
