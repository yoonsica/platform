package com.ceit.vic.platform.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ceit.vic.platform.exception.SessionTimeoutException;
import com.ceit.vic.platform.models.User;

public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {
	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除  
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
        System.out.println(requestUrl);  
        if(null != allowUrls && allowUrls.length>=1){  
            for(String url : allowUrls) {    
                if(requestUrl.contains(url)) {
                	return true;
                }    
            }
        }
        User user = (User) request.getSession().getAttribute("user");  
        if(user != null) {    
            return true;  //返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()  
        }else{  
        	 // 未登录   
        	 if (request.getHeader("x-requested-with") != null  
                     && request.getHeader("x-requested-with")  
                             .equalsIgnoreCase("XMLHttpRequest"))//如果是ajax请求响应头会有，x-requested-with；  
             {  
                 response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                 response.getWriter().print("timeout"); //打印一个返回值，没这一行，在tabs页中无法跳出（导航栏能跳出），具体原因不明  
                 return false;  
             }  
        	response.setCharacterEncoding("utf8");
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();  
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
            builder.append("alert(\"页面过期，请重新登录\");");  
            builder.append("window.location.href=\"");  
            builder.append("http://localhost:8080/platform");  
            builder.append("/jsp/login.jsp\";</script>");  
            out.print(builder.toString());  
            out.close(); 
            return false;
            //throw new SessionTimeoutException();//返回到配置文件中定义的路径  
        }
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
