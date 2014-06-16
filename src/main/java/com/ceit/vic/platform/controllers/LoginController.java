package com.ceit.vic.platform.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.service.DictionaryService;
import com.ceit.vic.platform.service.LogService;
import com.ceit.vic.platform.service.PersonService;
import com.ceit.vic.platform.service.ResourcesService;


@Controller
public class LoginController {
	static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	ResourcesService resourcesService;
	@Autowired
	PersonService personService;
	@Autowired
	LogService logService;
	@Autowired 
	DictionaryService dictionaryService;
	
	@RequestMapping("/toLogin")
	public ModelAndView toLogin(){
		ModelAndView mav = new ModelAndView("login");
		//mav.addObject("errorMsg","");
		return mav;
		
	}
	
	@RequestMapping(value="/valid",method=RequestMethod.POST)
	public ModelAndView valid(Person person,HttpSession session,HttpServletRequest request) {
		if (person.getCode()==null&&person.getPassword()==null) {
			System.out.println("null");
			return new ModelAndView("login");
		}else {
			//连接数据库验证
			List<Person> personList = personService.getPersonsByCodeName(person.getCode());
			if (null==personList||personList.size()==0) {
				ModelAndView mav = new ModelAndView("login");
				mav.addObject("errorMsg","用户名不存在");
				return mav;
			}else {
				for (Person person1 : personList) {
					if (person1.getPassword().toLowerCase().equals(person.getPassword().toLowerCase())) {
						session.setMaxInactiveInterval(Integer.valueOf(dictionaryService.getByName("MAXINACTIVEINTERVAL").get(0).getValue().toString()));
						session.setAttribute("user", person1);
						ModelAndView mav = new ModelAndView("south");
						Log log = new Log();
						log.setContent(person1.getCode()+"用户登陆成功");
						log.setPerson(person1);
						log.setType(new LogType(1));
						log.setIp(logService.getRemoteAddress(request));
						logService.addLog(log);
						return mav;
					}
				}
				ModelAndView mav = new ModelAndView("login");
				mav.addObject("errorMsg","密码错误");
				Log log = new Log();
				log.setContent(person.getCode()+"用户登陆失败：密码错误");
				log.setPerson(person);
				log.setType(new LogType(1));
				log.setIp(logService.getRemoteAddress(request));
				//logService.addLog(log);
				return mav;
			}
		}
	}
	
	@RequestMapping("/nav")
	@ResponseBody
	public List<NavItem> navInit(HttpServletRequest request){
		Person person = (Person) request.getSession().getAttribute("user");
		List<NavItem> menuList = resourcesService.getNavItems(person);
		return menuList;
	}
	
	
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		Person person = (Person) request.getSession().getAttribute("user");
		Log log = new Log();
		log.setType(new LogType(1));
		log.setIp(logService.getRemoteAddress(request));
		log.setPerson(person);
		log.setContent(person.getCode()+"用户注销成功");
		logService.addLog(log);
		request.getSession().removeAttribute("user");
		return "redirect:/toLogin";
	}
}
