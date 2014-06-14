$.ajaxSetup({ 
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
         complete:function(XMLHttpRequest,textStatus){
        	 var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
        	 if(sessionstatus=='timeout'){   
        		 	//alert("页面过期，请重新登录");
        		 	var location = (window.location+'').split('/'); 
        		 	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
        		      var topWindow = getTopWinow();  
                      topWindow.location.href=basePath+"/jsp/login.jsp"; 
              }
         }   
   }); 

function getTopWinow(){  
    var p = window;  
    while(p != p.parent){  
        p = p.parent;  
    }  
    return p;  
}  