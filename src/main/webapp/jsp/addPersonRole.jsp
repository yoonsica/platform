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
    
    <title>人员管理</title>
    
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
			$("#backBtn").click(function(){
				this.location.href = "role.jsp";
			});
			$('#test').datagrid({
				title:'人员列表',
				iconCls:'icon-save',
				width:600,
				height:'auto',
				fitColumns: true,
				url:"persons/${depId}",
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'姓名',width:80},
					{field:'departmentName',title:'所在部门',width:130},
					{field:'code',title:'用户名',width:80},
					{field:'state',title:'状态',width:30},
					{field:'memo',title:'备注',width:140}
				]],
				rownumbers:true,
				pagination:true,
				toolbar:[{
					id:'btnauthorize',
					text:'授权角色',
					iconCls:'icon-202',
					handler:function(){
						$('#btnauthorize').linkbutton('enable');
						alert('授权角色');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "addPersonRole/${roleId}",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               dataType: "json",
			               success:function(data){
			                   alert(data);
			               }  
	        			});
					}
				}]
			});
			var p = $('#test').datagrid('getPager');
			$(p).pagination({
				total:100,
				pageSize:10,
				
				onBeforeRefresh:function(){
					alert('before refresh');
				}
			});
		});
	</script>
  </head>
  
  <body>
	<table id="test"></table>
	<input type="button" id="backBtn" value="返回角色管理">
  </body>
</html>
