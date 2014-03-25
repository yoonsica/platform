package com.ceit.vic.platform.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
        	response.setCharacterEncoding("utf8");
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();  
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
            builder.append("alert(\"页面过期，请重新登录\");");  
            builder.append("window.top.location.href=\"");  
            builder.append("http://localhost:8080/platform");  
            builder.append("/jsp/login.jsp\";</script>");  
            out.print(builder.toString());  
            out.close();   
            //throw new SessionTimeoutException();//返回到配置文件中定义的路径  
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
