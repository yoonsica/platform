<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>Tabs - jQuery EasyUI Demo</title>
	<%--<link rel="stylesheet" type="text/css" href="static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="static/easyui/themes/icon.css">
	<script type="text/javascript" src="static/easyui/jquery.easyui.min.js"></script>
	--%><script type="text/javascript" src="static/easyui/jquery-1.7.2.min.js"></script>
	
	<%--<style type="text/css">
		ul{list-style-type:none; margin:0;width:100%; }
		ul li{ width:80px; float:left;}
	</style>
	--%><script type="text/javascript">
	$(function() {
		$("a").click(function(){
			$(window.parent.frames["center"].document.getElementById("westDiv")).load("jsp/MyJsp.jsp");
			window.parent.frames["center"].addTab();//调用south.jsp页面的addTab()方法，直接在这里添加tab会报错“option对象为空”
			return false;
			//alert(window.parent.frames["center"].document.getElementById("southDiv").innerHTML);
		});
	})
	</script>
</head>
<body style="margin: 0;padding: 0;">
	<div style="height:59px;width:100%;background:url('static/images/banner3.jpg') no-repeat;"><a style="position:absolute ;left:1280px ;top:35px; color: white;font-weight:800 ;font-size:18px ; text-decoration: none;" href="?">注销</a></div>
	<%--<div style="padding:2px;background:url('static/images/linebg.jpg')";width:500px;">
		<a href="jsp/MyJsp.jsp" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">全景信息展示</a>
		<a href="#" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">运行风险扫描</a>
		<a href="#" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">状态智能诊断</a>
		<a href="#" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">状态综合分析</a>
		<a href="#" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">试验数据挖掘</a>
		<a href="#" class="easyui-linkbutton" plain="true" style="margin-right: 20px;">系统维护</a>
	</div>--%>
</body>
</html>