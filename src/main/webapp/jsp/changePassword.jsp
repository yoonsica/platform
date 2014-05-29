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
    
    <title>修改密码</title>
    
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
	<script>
		$(function(){
			$("#cancelBtn").click(function(){
				window.location.href = "${basePath }jsp/welcome.jsp";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "changePassword",
	                data:$("#passwordForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
				        window.location.href = "${basePath}jsp/welcome.jsp";;
	                }  
	         	});
			});
		});
	</script>
  </head>
  
  <body>
  	<form id="passwordForm">
  		<table>
			<tr>
				<td>旧密码</td><td><input type="text" name="oldPassword"></td>
			</tr>
			<tr>
				<td>新密码</td><td><input type="text" name="newPassword"></td>
			</tr>
			<tr>
				<td>再次输入新密码</td><td><input type="text" name="newPassword2"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input id="submitBtn" type="submit" value="确定"><input type="button" id="cancelBtn" value="取消"></td>
			</tr>
		</table>
  	</form>
  </body>
</html>
