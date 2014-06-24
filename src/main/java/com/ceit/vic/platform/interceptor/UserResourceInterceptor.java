package com.ceit.vic.platform.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ceit.vic.platform.exception.NoPermissionException;
import com.ceit.vic.platform.models.Log;
import com.ceit.vic.platform.models.LogType;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.service.LogService;
import com.ceit.vic.platform.service.ResourcesService;
import com.ceit.vic.platform.service.RoleService;


public class UserResourceInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	ResourcesService resourcesService;
	@Autowired
	LogService logService;
	@Autowired
	RoleService roleService;
	public String[] allowUrls;  
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
		 if(null != allowUrls && allowUrls.length>=1){  
	            for(String url : allowUrls) {    
	                if(requestUrl.contains(url)) {
	                	return true;
	                }    
	            }
	        }
		Person person = (Person) request.getSession().getAttribute("user");
		if (null!=person) {
			if (roleService.getRoleIdByPersonId(person.getId()).contains(0)==true) {
				return true;
			}
	        System.out.println(requestUrl);
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("link", requestUrl.substring(1));
	        //List<Resources> resources = resourcesService.getByParamMap(map);
	        List<Resources> resList = resourcesService.getByLink(requestUrl.substring(1));
	        if (null==resList||resList.size()==0) {
				return true;
			}
	        for (Resources resources : resList) {
	        	if(resourcesService.isAuthOrNot(person, resources.getId())){
	        		Log log = new Log();
	        		log.setIp(logService.getRemoteAddress(request));
	        		log.setPerson(person);
	        		StringBuffer sb = new StringBuffer();
	        		sb.append(person.getCode()).append("(ip:")
	        		.append(log.getIp()).append(")")
	        		.append("访问了")
	        		.append(resources.getName())
	        		.append(",访问地址:").append(resources.getLink());
	        		log.setContent(sb.toString());
	        		log.setType(new LogType(2));
	        		logService.addLog(log);
	        		return true;
	        	};
			}
	     
	        if (request.getHeader("x-requested-with") != null  
                    && request.getHeader("x-requested-with")  
                            .equalsIgnoreCase("XMLHttpRequest"))//如果是ajax请求响应头会有，x-requested-with；  
            {  
	        	response.setContentType("application/x-www-form-urlencoded;charset=utf-8");
                response.setHeader("sessionstatus", "nopermission");//在响应头设置session状态
                response.getWriter().print("您没有该项权限！"); //打印一个返回值，没这一行，在tabs页中无法跳出（导航栏能跳出），具体原因不明  
                return false;  
            } else {
            	throw new NoPermissionException();//返回到配置文件中定义的路径  
			} 
	        
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	}

}
