<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统模块管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
  	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
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
	}
	
	input { width: 128px; }
	
	label { left-margin: 10px; }
	
    .base-div { 
    	width: 720px; 
    	height: 600px; 
    	left: 50%; 
    	margin: 0 0 0 -360px; 
    	position: absolute; 
    }
	</style>
  	<script type="text/javascript">
	 	
		$(document).ready(function(){
			//设置分页控件  
		    $('#log').datagrid('getPager').pagination({  
		        beforePageText: '第',//页数文本框前显示的汉字  
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		    });  
			/*$("#log").datagrid({
				title: "日志列表",
				width: 640,
				height: 600,
				url: "persons/${depId}",
				frozenColumns:[[
			        {field:"ck", checkbox:true}
				]],
				columns:[[
					{ field:"id", title:"id", width: 40 },
					{ field:"content", title:"日志内容", width: 200 }, 
					{ field:"type", title:"类型", width: 40 }, 
					{ field:"user", title: "用户", width: 80 }, 
					{ field:"IP", title:"IP", width: 120 }, 
					{ field:"module", title: "功能模块", width: 120 },
				]],
				pagination:true
		 	});*/
		});
    </script>
  </head>
  
  <body>
	<div class="base-div">
		<form>
			<fieldset>
				<legend>查询信息</legend>
				<div>
					<label for="logContent">日志内容</label>
					<input type="text" name="logContent" />
					<label for="userName">用户名称</label>
					<input type="text" name="userName" />
					<label for="userIP">用户IP</label>
					<input type="text" name="userIP" />
				</div>
				<div>
					<label for="beginTime">时间范围</label>
					<input class="easyui-datetimebox" name="beginTime"   
        				data-options="required:true, showSeconds:false" />
        			<span>--></span>
					<input class="easyui-datetimebox" name="endTime"   
        				data-options="required:true, showSeconds:false" />
        			<label for="logType">时间范围</label>
					<select name="logType">
						<option value ="1">登录</option>  
						<option value ="2">访问</option>  
						<option value="3">系统</option>  
					</select>
					<a id="inquiryBtn" href="#" class="easyui-linkbutton" 
						data-options="iconCls: 'icon-search'">查询</a>
					<a id="deleteBtn" href="#" class="easyui-linkbutton" 
						data-options="iconCls: 'icon-remove'">删除</a>
				</div>
			</fieldset>
		</form>
		<table id="log" class="easyui-datagrid" title="日志列表" style="width: 640px;" 
			url="logManage/show" singleSelect="true" pagination="true" pageSize="10">
	        <thead>
	            <tr>
	            	<th field="check" data-options="checkbox: true"></th>
	                <th field="id" width="40">ID</th>
	                <th field="content" width="200">日志内容</th>
	                <th field="type" width="40">类型</th>
	                <th field="user" width="80">用户</th>
	                <th field="ip" width="80">IP</th>
	                <th field="time" width="100">记录时间</th>
	            </tr>
	        </thead>
	    </table>
    </div>
  </body>
</html>
