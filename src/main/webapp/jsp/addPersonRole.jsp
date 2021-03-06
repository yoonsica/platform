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
    
    <title>给人员添加角色</title>
    
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
			$("#backBtn").click(function(){
				window.parent.location.href = "${basePath}jsp/role.jsp";
			});
			$('#test').datagrid({
				title:'人员列表',
				iconCls:'icon-save',
				width:800,
				fitColumns: true,
				url:"persons/${depId}",
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'姓名',width:60},
					{field:'departmentName',title:'所在部门',width:300},
					{field:'code',title:'用户名',width:80},
					{field:'state',title:'状态',width:30},
					{field:'memo',title:'备注',width:100}
				]],
				rownumbers:true,
				pagination:true,
				toolbar:[{
					id:'btnauthorize',
					text:'授权角色',
					iconCls:'icon-202',
					handler:function(){
						$('#btnauthorize').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "addPersonRole/${roleId}",
        	               contentType: "application/x-www-form-urlencoded; charset=utf-8",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
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
  
  <body class="easyui-layout">
  	<div region="center" style="padding:5px;" border="false">
  
  	<table>
  		<tr><td><table id="test"></table></td></tr>
  		<tr><td align="center"><input type="button" id="backBtn" value="返回角色管理"></td></tr>
  	</table>
  	</div>
  	
  </body>
</html>
