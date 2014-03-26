<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
	table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;}
th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;}
th{font-weight:bold;background:#ccc;}
	</style>
  </head>
  <body style="margin: 0;padding: 0;">
    <table border="1" width="600px">
    	<thead>
    		<tr><td colspan="2">当前选中模块信息</td></tr>
    	</thead>
    	<tbody>
    		<tr>
    			<td>ID</td><td>${moduleInfo.id }</td>
    		</tr>
    		<tr>
    			<td>名称</td><td>${moduleInfo.name }</td>
    		</tr>
    		<tr>
    			<td>类别</td><td>${moduleInfo.parent }</td>
    		</tr>
    		<tr>
    			<td>链接</td><td>${moduleInfo.link }</td>
    		</tr>
    		<tr>
    			<td>状态</td><td>${moduleInfo.state }</td>
    		</tr>
    	</tbody>
    </table>
    
      <div id="moduleEditDiv" style="height: 500px;margin-left:250px;">
  		<form id="moduleInfoForm" method="post" novalidate>
	        <div>
	            <label for="name">Id:</label>
	            <input class="easyui-validatebox" type="text" name="name" required="true" value="${moduleInfo.id }" disabled="disabled"></input>
	        </div>
	        <div>
	            <label for="email">Email:</label>
	            <input class="easyui-validatebox" type="text" name="email" validType="email"></input>
	        </div>
	        <div>
	            <label for="subject">Subject:</label>
	            <input class="easyui-validatebox" type="text" name="subject"></input>
	        </div>
	        <div>
	            <label for="message">Message:</label>
	            <textarea name="message" style="height:60px;"></textarea>
	        </div>
	        <div>
	            <input type="submit" value="确认">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
