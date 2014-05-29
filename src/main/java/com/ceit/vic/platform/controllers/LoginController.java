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

import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.service.PersonService;
import com.ceit.vic.platform.service.ResourcesService;


@Controller
public class LoginController {
	static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	ResourcesService resourcesService;
	@Autowired
	PersonService personService;
	
	@RequestMapping("/toLogin")
	public ModelAndView toLogin(){
		ModelAndView mav = new ModelAndView("login");
		//mav.addObject("errorMsg","");
		return mav;
		
	}
	
	@RequestMapping(value="/valid",method=RequestMethod.POST)
	public ModelAndView valid(Person person,HttpSession session) {
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
						session.setMaxInactiveInterval(600);
						session.setAttribute("user", person1);
						ModelAndView mav = new ModelAndView("south");
						return mav;
					}
				}
				ModelAndView mav = new ModelAndView("login");
				mav.addObject("errorMsg","密码错误");
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
}
