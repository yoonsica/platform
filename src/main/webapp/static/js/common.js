$.ajaxSetup({ 
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
         complete:function(XMLHttpRequest,textStatus){
        	 var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //ͨ��XMLHttpRequestȡ����Ӧͷ��sessionstatus��  
        	 if(sessionstatus=='timeout'){   
        		 	alert("��¼��ʱ�������µ�¼");
        		 	var location = (window.location+'').split('/'); 
        		 	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
                      //�����ʱ�ʹ��� ��ָ��Ҫ��ת��ҳ��  
        		      var top = getTopWinow();  //��ҳ�����κ�Ƕ�ײ�εĴ����л�ȡ���㴰��
                      top.location.href=basePath+"/jsp/login.jsp"; //pathΪȫ�ֱ�����
              }
         }   
   }); 
/** 
  * ��ҳ�����κ�Ƕ�ײ�εĴ����л�ȡ���㴰�� 
  * @return ��ǰҳ��Ķ��㴰�ڶ��� 
  */
function getTopWinow(){  
    var p = window;  
    while(p != p.parent){  
        p = p.parent;  
    }  
    return p;  
}  