<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加模块内按钮和链接</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
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
	</style>
	<script>
		$(function(){
			$("#cancelBtn").click(function(){
			window.parent.window.refreshTree("${resId }");
				//window.location.href = "${basePath }jsp/buttonLink.jsp";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "addButtonLink",
	                data:$("#buttonLinkForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
				        window.parent.window.refreshTree("${resId }");
	                }  
	         	});
			});
		});
	</script>
  </head>
  
  <body>
  	<form id="buttonLinkForm">
  		<input type="hidden" name="parent" value="${resId }">
  		<table>
			<tr>
				<td>名称</td><td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>类别</td><td><input type="radio" name="type" value="1">按钮 <input type="radio" name="type" value="2">链接 </td>
			</tr>
			<tr>
				<td>请求地址</td><td><textarea name="link"></textarea> </td>
			</tr>
			<tr>
				<td>备注</td><td><textarea name="memo"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input id="submitBtn" type="button" value="确定">&nbsp;&nbsp;&nbsp;<input type="button" id="cancelBtn" value="取消"></td>
			</tr>
		</table>
  	</form>
  </body>
</html>
