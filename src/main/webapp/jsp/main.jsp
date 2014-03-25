<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>平台主页</title>
	<link rel="stylesheet" type="text/css" href="static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="static/easyui/themes/icon.css">
	<script type="text/javascript" src="static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="static/easyui/jquery.easyui.min.js"></script>
</head>
<body style="margin: 0;padding: 0;border:none;overflow:hidden;">
	<iframe name="top"  width="100%" height="89" frameborder="0" border=0 scrolling="no" style="margin: 0;padding: 0;border:none;overflow:hidden;" src="jsp/logo.jsp"></iframe>
	<iframe name="center"  width="100%" height="614" frameborder="0" border=0 scrolling="no" style="margin: 0;padding: 0;border:none;overflow:hidden;" src="jsp/south.jsp"></iframe>
	
</body>
</html>