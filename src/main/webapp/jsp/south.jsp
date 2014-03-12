<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>Tabs - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="static/easyui/themes/icon.css">
	<script type="text/javascript" src="static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="static/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" href="static/nav/style.css" type="text/css" media="screen" charset="utf-8"/>
    <SCRIPT language="JavaScript" SRC="static/nav/IE-fix.js"></SCRIPT>
	<script type="text/javascript">
	function addTab(){
		
		$('#tt').tabs('add',{
			title:'New Tab ' ,
			content:'Tab Body ',
			closable:true
		});
	}
	$(function() {
		var myDate = new Date();
		$("#southDiv").html("${user.userName }&nbsp;"+myDate.toLocaleDateString());
	})
	</script>

</head>
<body class="easyui-layout">
	<div id="container" region="north" style="overflow: hidden;">
		<div id="nav_slim">
		<ul>
			<li><a href="#">Home</a></li>
			
			<li><a href="#" id="active">Services</a>
				<ul>
					<li><a href="#">Service 1</a></li>
					<li><a href="#">Service 2</a></li>
					<li><a href="#">Service 3</a></li>
			   </ul>
		 	</li>
		 	<li><a href="#">About Us</a></li>
			<li><a href="#">Our Work</a>
		        <ul>
			        <li><a href="#">Web</a></li>
			        <li><a href="#">Print</a></li>
			        <li><a href="#">Logo</a></li>
			        <li><a href="#">Other</a></li>
		        </ul>
		  </li>
			<li><a href="#">Contact</a></li>
		</ul>
	</div>
		<div class="clearFix"></div>
	</div>
	<div id="westDiv" region="west" split="true" title="功能列表" style="width:150px;padding:10px;">
		<ul class="easyui-tree" url="${basePath }static/tree_data.json"></ul>
	</div>
	
	<div id="southDiv" region="south" border="false" style="height:20px;background:url('static/images/linebg.jpg');padding:1px 25px;text-align: right;font-weight:bold;color: #000000;">
		
	</div>
	
	<div region="center" style="overflow: hidden;">
		<div id="tt" class="easyui-tabs" style="width:1335;height:566px;background:url(static/images/bg.jpg) no-repeat top center;">
			<div title="这是一个标签页" closable="true" style="padding:20px;" cache="false" href="jsp/MyJsp.jsp">
				This is Tab2 with close button.
			</div>
		</div>
	</div>
</body>
</html>