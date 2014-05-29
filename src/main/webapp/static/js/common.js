$.ajaxSetup({ 
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
         complete:function(XMLHttpRequest,textStatus){
        	 var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
        	 if(sessionstatus=='timeout'){   
        		 	alert("登录超时，请重新登录");
        		 	var location = (window.location+'').split('/'); 
        		 	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
                      //如果超时就处理 ，指定要跳转的页面  
        		      var top = getTopWinow();  //在页面中任何嵌套层次的窗口中获取顶层窗口
                      top.location.href=basePath+"/jsp/login.jsp"; //path为全局变量，
              }
         }   
   }); 
/** 
  * 在页面中任何嵌套层次的窗口中获取顶层窗口 
  * @return 当前页面的顶层窗口对象 
  */
function getTopWinow(){  
    var p = window;  
    while(p != p.parent){  
        p = p.parent;  
    }  
    return p;  
}  