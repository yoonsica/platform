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
    
    <title>人员信息</title>
    
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
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
  	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<style type="text/css">
	body {
		FONT-SIZE: 14px;
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
	
	h2 { font-size: 1.4em; }
	
    .base-div { 
    	width: 1200px; 
    	height: 500px; 
    	left: 50%; 
    	margin: 0 0 0 -600px; 
    	position: absolute; 
    }
    
    .info-div {
    	width: 360px;
    	height: 500px;
    	overflow: auto;
    	float: left;
    }
    
    .info-div h2 {
   		margin-left: 30px;
   	}
    .info-div p {
    	margin-left: 80px;
    	margin-top: 10px;
    }
    
    .relation-div {
    	width: 840px;
    	height: 500px;
    	overflow: auto;
    	float: right;
    }
    
	</style>
  	<script type="text/javascript">
	  	
	  	function getQueryParams(id) {
	  		return { id: id, type: "person" };
	  	}
  	
  		function stateFormatter(state) {
  			return state == 0 ? '停用' : '启用';
  		}
  		
	  	function formatNumber(n) {
			return n < 10 ? ('0' + n) : n;
		}
		
		function dateFormatter(date) {
			// 将毫秒转成日期
			var date = new Date(date);
			var y = date.getFullYear();  
	        var M = date.getMonth() + 1;  
	        var d = date.getDate();  
	        var h = date.getHours();
	        var m = date.getMinutes();
	        var s = date.getSeconds();
	        return y + '-' + formatNumber(M) + '-' + formatNumber(d) + ' ' + formatNumber(h) + ':' + formatNumber(m) + ':' + formatNumber(s);
		}
		
		function typeFormatter(type) {
			return type.name;
		}
		
		$(document).ready(function(){
			$('#roleTable').datagrid({
				title: "角色列表",
				width: 840,
				height: 150,
				url: "SiteSearch/getRoles", 
				queryParams: getQueryParams(${person.id }), 
				singleSelect: true, 
				columns:[[
					{field:'id',title:'id',hidden: true},
					{field:'name',title:'名称',width: 300},
					{field:'memo',title:'备注',width: 500}
				]],
				pagination:true, 
				onClickRow: function(rowIndex, rowData) {
		    		window.location.href = "SiteSearch/showRoleInfo?id=" + rowData.id;
			    }
			});
			
			$('#resourceTable').datagrid({
				title: "资源列表",
				width: 840,
				height: 150,
				url: "SiteSearch/getResources", 
				queryParams: getQueryParams(${person.id }), 
				singleSelect: true, 
				columns:[[
					{field:'id',title:'id',hidden: true},
					{field:'name',title:'名称',width: 300},
					{field:'memo',title:'备注',width: 400}, 
					{field:'state',title:'状态',width: 100, formatter: stateFormatter}
				]],
				pagination:true, 
				onClickRow: function(rowIndex, rowData) {
		    		window.location.href = "SiteSearch/showResourceInfo?id=" + rowData.id;
			    }
			});
			
			$('#logTable').datagrid({
				title: "日志列表",
				width: 840,
				height: 200,
				url: "SiteSearch/getLogs", 
				queryParams: getQueryParams(${person.id }), 
				singleSelect: true, 
				columns:[[
					{field:'id',title:'id',hidden: true},
					{field:'content',title:'内容',width: 400},
					{field:'type',title:'类型',width: 100, formatter: typeFormatter }, 
					{field:'ip',title:'IP',width: 150}, 
					{field:'recordTime',title:'记录时间',width: 164,  formatter: dateFormatter }
				]],
				pagination:true
			}); 
		});
		
    </script>
  </head>
  
  <body>
	<div class="base-div">
		<div id="infoDiv" class="info-div">
			<h2>用户信息</h2>
			<p><span>ID：</span><span>${person.id }</span></p>
			<p><span>姓名：</span><span>${person.name }</span></p>
			<p><span>性别：</span><span>${person.sex }</span></p>
			<p><span>所在部门：</span><a href="SiteSearch/showDepartmentInfo?id=${department.id }">${department.name }</a></p>
			<p><span>用户名：</span><span>${person.code }</span></p>
			<p><span>状态：</span><span>${person.state }</span></p>
			<p><span>备注：</span><span>${person.memo }</span></p>
		</div>
		<div id="relationDiv" class="relation-div">
			<table id="roleTable"></table>
			<table id="resourceTable"></table>
			<table id="logTable"></table>
		</div>
    </div>
  </body>
</html>
