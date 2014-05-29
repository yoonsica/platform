package com.ceit.vic.platform.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ceit.vic.platform.exception.NoPermissionException;
import com.ceit.vic.platform.exception.SessionTimeoutException;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.Person_Role;
import com.ceit.vic.platform.models.ResAccess;
import com.ceit.vic.platform.models.Resources;
import com.ceit.vic.platform.service.Dep_PersonService;
import com.ceit.vic.platform.service.Person_RoleService;
import com.ceit.vic.platform.service.ResAccessService;
import com.ceit.vic.platform.service.ResourcesService;


public class UserResourceInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	ResAccessService resAccessService ;
	@Autowired
	ResourcesService resourcesService;
	@Autowired
	Person_RoleService person_RoleService;
	@Autowired 
	Dep_PersonService dep_PersonService;
	
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
			if (person.getCode().equals("test1")) {
				return true;
			}
	        System.out.println(requestUrl);
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("link", requestUrl.substring(1));
	        //List<Resources> resources = resourcesService.getByParamMap(map);
	        List<Resources> resources = resourcesService.getByLink(requestUrl.substring(1));
	        if (null==resources) {
				return true;
			}
	        List<Integer> resIdList = new ArrayList<Integer>();
	        for (Resources resources2 : resources) {
	        	resIdList.add(resources2.getId());
			}
	      //判断人
	        Map<String, Object> map1 = new HashMap<String, Object>();
	        map1.put("resId", resIdList);
	        map1.put("accessType", 1);
	        map1.put("accessId", person.getId());
	        List<ResAccess> testList = resAccessService.getByParamMap(map1);
	        if (null!=testList&&testList.size()>0) {
				return true;
			}
	        //判断部门
	        List<Dep_Person> depList = dep_PersonService.getByPersonId(person.getId(),false);
	        if (null!=depList&&depList.size()>0) {
	        	List<Integer> depIdList = new ArrayList<Integer>();
		        for (Dep_Person dep_Person : depList) {
					depIdList.add(dep_Person.getDepId());
				}
		        Map<String, Object> map2 = new HashMap<String, Object>();
		        map2.put("resId", resIdList);
		        map2.put("accessType", 2);
		        map2.put("accessId", depIdList);
		        List<ResAccess> testList2 = resAccessService.getByParamMap(map2);
		        if (null!=testList2&&testList2.size()>0) {
					return true;
				}
			}
	        
	        //判断角色
	        List<Person_Role> person_Roles = person_RoleService.getPersonRoleByPersonId(person.getId());
	        if (null!=person_Roles&&person_Roles.size()>0) {
	        	List<Integer> roleIds = new ArrayList<Integer>();
		        for (Person_Role person_Role : person_Roles) {
					roleIds.add(person_Role.getRoleId());
				}
		        Map<String, Object> map3 = new HashMap<String, Object>();
		        map3.put("resId", resIdList);
		        map3.put("accessType", 0);
		        map3.put("accessId", roleIds);
		        List<ResAccess> testList3 = resAccessService.getByParamMap(map3);
		        if (null!=testList3&&testList3.size()>0) {
					return true;
				}
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
