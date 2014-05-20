package com.ceit.vic.platform.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.platform.models.NavItem;
import com.ceit.vic.platform.models.User;
import com.ceit.vic.platform.service.ResourcesService;


@Controller
public class LoginController {
	static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	ResourcesService resourcesService;
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(User user,HttpSession session) {
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();*/
		logger.debug("login");
		try {
			System.out.println(user.getUserName()+"pp:"+user.getUserPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user.getUserName()==null&&user.getUserPassword()==null) {
			System.out.println("null");
			return new ModelAndView("login");
		}else {
			//连接数据库验证
			if (user.getUserName().equals("test")) {
				if (user.getUserPassword().equals("c7a6ea7bef62600e3ac4852835cfc7c4")) {
					System.out.println(user.getUserName());
					session.setMaxInactiveInterval(20);
					System.out.println(user);
					session.setAttribute("user", user);
					ModelAndView mav = new ModelAndView("south");
					/*List<NavItem> menuList = resourcesService.getNavItems();
					mav.addObject("menuList",menuList);*/
					return mav;
				}
			}
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping("/nav")
	@ResponseBody
	public List<NavItem> navInit(){
		List<NavItem> menuList = resourcesService.getNavItems();
		return menuList;
	}
}
