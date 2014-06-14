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
		<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
	<script type="text/javascript" src="${basePath }static/js/md5.js"></script>
	<script>
		$(function(){
			$("#cancelBtn").click(function(){
				window.location.href = "${basePath }jsp/welcome.jsp";
			});
			$("#submitBtn").click(function(){
				if($("#newPassword1").text()!=$("#newPassword2").text()){
					alert("两次输入不一致，请重新输入！");
					return false;
				}
				var hash=hex_md5(document.getElementById("newPassword2").value);  
				document.getElementById("newPassword").value=hash;
				var hash=hex_md5(document.getElementById("oldPassword").value);  
				document.getElementById("oldPasswordMd5").value=hash;
				$.ajax({  
	                type: "POST",  
	                url: "changePassword",
	                data:$("#passwordForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	if(data=="true"){
	                		alert("修改成功！");
	                		window.parent.window.update("欢迎使用","${basePath}jsp/welcome.jsp");
	                	}else{
	                		alert("旧密码输入错误！");
	                		location.reload();
	                	}
	                }  
	         	});
			});
		});
	</script>
  </head>
  
  <body>
  <div class="container">
  	<form id="passwordForm">
  		<table style="margin-left: auto;margin-right: auto;">
			<tr>
				<td>旧密码</td><td><input type="password" name="oldPassword" id="oldPassword"></td>
			</tr>
			<tr>
				<td>新密码</td><td><input type="password" name="newPassword1" id="newPassword1"></td>
			</tr>
			<tr>
				<td>再次输入新密码</td><td><input type="password" name="newPassword2" id="newPassword2"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input id="submitBtn" type="button" value="确定"><input type="button" id="cancelBtn" value="取消"></td>
			</tr>
		</table>
		<input type="hidden" name="oldPasswordMd5" id="oldPasswordMd5"/>
		<input type="hidden" name="newPassword" id="newPassword"/>
  	</form>
  	</div>
  </body>
</html>
