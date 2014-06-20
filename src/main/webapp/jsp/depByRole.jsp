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
    
    <title>部门列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
	<script type="text/javascript" src="${basePath }static/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script>
		$(function(){
			$('#test').datagrid({
				title:'部门列表',
				iconCls:'icon-save',
				width:870,
				height:530,
				fitColumns: true,
				url:"getDepsByRoleId/${roleId}",
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'所在部门',width:130},
					{field:'memo',title:'备注',width:140}
				]],
				rownumbers:true,
				pagination:true,
				toolbar:[{
					id:'btnadd',
					text:'添加部门',
					iconCls:'icon-add',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						window.parent.location.href="${basePath}toAddDepRole/${roleId}";
					}
				},'-',{
					id:'btndelete',
					text:'取消授权',
					iconCls:'icon-remove',
					handler:function(){
						$('#btndelete').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "cancleRole/${roleId}",
		               	   contentType: "application/x-www-form-urlencoded; charset=utf-8",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               	alert(data);
			                   window.location.reload();
			               }  
	        			});
					}
				}
				]
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
	
  </body>
</html>
