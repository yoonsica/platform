<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加目录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
	
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
#moduleEditDiv div{
	margin-bottom: 5px;
}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#cancleBtn").click(function(){
				window.location.href = "${basePath}moduleInfo/${parent.id }";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "moduleManage/AddFolder",
	                data:$("#moduleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert("添加成功！");
	                	$(window.parent.document.getElementById("menuDiv")).show();
	                	$("#moduleEditDiv").hide();
	                	$("#infoTable").show();
	                	window.parent.window.refreshTree(data);//刷新树
	                	if($("#parent").attr("value")==1){
	                		alert("刷新菜单项");
	                		window.parent.parent.window.navInit();
	                	}
	                	window.parent.parent.window.refreshWestDiv(null);
	                }  
	         	});
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
      <div id="moduleEditDiv" style="background:#fafafa;padding:10px;">
  		<form id="moduleInfoForm" method="post" >
  		      <input type="hidden" name="parent" value="${parent.id }"/>
	        <div>
	            <label for="name">名称</label>
	            <input type="text" name="name"/>
	        </div>
	        <div >
	            <label for="parent">类别</label>
	            <input type="text" name="parentName" id="parentName" value="${parent.name }" disabled="disabled"/>
	        </div>
	        <div style="height: 250px;">
	            <label for="icon">功能图标</label><br>
	            <c:forEach items="${iconList }" var="icon" varStatus="status">
						<div style="line-height: 18px;float:left;margin-right:10px;height: 18px;width: 50px;">
							<input name="icon" type="radio" value="${icon}" />
							<img src="static/images/icons/${icon}" width="18" height="18" style="margin-left: -5px;"/>
						</div>
	            </c:forEach>
	        </div>
	        <div style="clear:left;margin-left: auto;margin-right: auto;TEXT-ALIGN: center;">
	            <input type="button" value="确认" id="submitBtn">
	            <input type="button" value="取消" id="cancleBtn">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
