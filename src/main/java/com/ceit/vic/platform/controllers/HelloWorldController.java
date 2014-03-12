package com.ceit.vic.platform.controllers;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ceit.vic.platform.models.User;

@Controller
public class HelloWorldController {
	static Logger logger = Logger.getLogger(HelloWorldController.class);
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String login(User user,HttpSession session) {
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();*/
		logger.debug("login");
		if (user.getUserName()==null&&user.getUserPassword()==null) {
			System.out.println("null");
			return "login";
		}else {
			if (user.getUserName().equals("test")) {
				logger.debug(user.getUserPassword());
				if (user.getUserPassword().equals("c7a6ea7bef62600e3ac4852835cfc7c4")) {
					System.out.println(user.getUserName());
					session.setMaxInactiveInterval(20);
					System.out.println(user);
					session.setAttribute("user", user);
					return "main";
				}
			}
		}
		return "login";
	}
	
	@RequestMapping("/helloworld.do")
	public String helloWorld(){
		System.out.println("helloWorld!");
		return "main";
	}
}
