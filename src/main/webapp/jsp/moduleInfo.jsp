<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'moduleInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<style type="text/css">
body {
	FONT-SIZE: 12px;
	BACKGROUND: #FFFFFF; 
	SCROLLBAR-FACE-COLOR: #bfebd2; 
    SCROLLBAR-HIGHLIGHT-COLOR: #94dc94; 
   SCROLLBAR-SHADOW-COLOR: #ade2c6; 
   SCROLLBAR-3DLIGHT-COLOR: #ade2c6; 
   SCROLLBAR-ARROW-COLOR: #73a790; 
   SCROLLBAR-TRACK-COLOR: #e9efeb; 
   SCROLLBAR-DARKSHADOW-COLOR: #8ac7a4; 
   SCROLLBAR-BASE-COLOR: #168a16; 
  FONT-FAMILY: "����", "Verdana", "Arial";
	margin-top: 0px; 
	margin-left: 2px; 
	margin-right: 0px; 
	overflow-y: auto
}
textarea {
	BORDER-TOP-WIDTH: 1px; 
	BORDER-LEFT-WIDTH: 1px; 
	FONT-SIZE: 12px; 
	BORDER-BOTTOM-WIDTH: 1px; 
	BORDER-RIGHT-WIDTH: 1px
}
td {
	FONT-SIZE: 12px;
	padding: 3px;
	font-size: 12px;
}
tr{
	background-color: #ffffff;
}
#moduleEditDiv div{
	margin-bottom: 5px;
}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "moduleUpdate",
	                data:$("#moduleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	//刷新整个页面
	                	alert("更新成功！");
	                	$(window.parent.document.getElementById("menuDiv")).show();
	                	$("#moduleEditDiv").hide();
	                	$("#infoTable").show();
	                	window.location.reload(true);
	                	window.parent.window.refreshTree(null);//刷新树
	                }  
	         	});
			});
			$("#cancleBtn").click(function(){
               	$(window.parent.document.getElementById("menuDiv")).show();
               	$("#moduleEditDiv").hide();
               	$("#infoTable").show();
               	//window.location.reload(true);
               	//window.parent.window.refreshTree(null);//刷新树
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
    <table border="0" bgcolor="#cccccc" cellspacing="1" cellpadding="5" width="100%" id="infoTable" class="toptablebg">
    	<thead>
    		<tr><td colspan="2" align="center" style="font-weight: bolder;">当前选中模块信息</td></tr>
    	</thead>
    	<tbody>
    		<tr >
    			<td align="center">ID</td><td>${moduleInfo.id }</td>
    		</tr>
    		<tr>
    			<td align="center">名称</td><td>${moduleInfo.name }</td>
    		</tr>
    		<tr>
    			<td align="center">类别</td><td>${moduleInfo.parent }</td>
    		</tr>
    		<tr>
    			<td align="center">链接</td><td>${moduleInfo.link }</td>
    		</tr>
    		<tr>
    			<td align="center">状态</td><td>${moduleInfo.state }</td>
    		</tr>
    	</tbody>
    </table>
    
      <div id="moduleEditDiv" style="display:none;background:#fafafa;padding:10px;">
  		<form id="moduleInfoForm" method="post" >
            <input type="hidden" name="id"  value="${moduleInfo.id }" ></input>
	        <div>
	            <label for="name">名称</label>
	            <input  type="text" name="name"  value="${moduleInfo.name }"></input>
	        </div>
	        <div >
	            <label for="parent">类别</label>
	            <input type="text" name="parent" value="${moduleInfo.parent }"></input>
	        </div>
	        <div style="line-height: 60px;height: 60px;float:left;">
	          	 <label for="link">链接</label>
	        </div>
	        <div style="margin-left: 3px;float: left;">
	        	<textarea name="link" style="height: 60px;">${moduleInfo.link }</textarea>
	        </div>
	        <div style="clear: left;">
	            <label for="state">状态</label>
	            <input type="radio" name="state"  value="1" <c:if test="${moduleInfo.state=='1' }">checked="checked"</c:if>>启用
	            <input type="radio" name="state"  value="0" <c:if test="${moduleInfo.state=='0' }">checked="checked"</c:if>>停用
	        </div>
	        <div style="height: 250px;">
	            <label for="icon">功能图标</label><br>
	            <c:forEach items="${iconList }" var="icon" varStatus="status">
	            	<c:choose>
	            		<c:when test="${moduleInfo.icon == icon}">
	            			<div style="line-height: 18px;float:left;margin-right:10px;height: 18px;width: 50px;">
		            		<input name="icon" type="radio" value="${icon}" checked="checked"/>
							<img src="static/images/icons/${icon}" width="18" height="18" style="margin-left: -5px;"/>
							</div>
							
						</c:when>
						<c:otherwise>
						<div style="line-height: 18px;float:left;margin-right:10px;height: 18px;width: 50px;">
							<input name="icon" type="radio" value="${icon}" />
							<img src="static/images/icons/${icon}" width="18" height="18" style="margin-left: -5px;"/>
						</div>
						</c:otherwise>
					</c:choose>
	            </c:forEach>
	        </div>
	        <div style="margin-left: auto;margin-right: auto;TEXT-ALIGN: center;">
	            <input type="button" value="确认" id="submitBtn">
	            <input type="button" value="取消" id="cancleBtn">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
