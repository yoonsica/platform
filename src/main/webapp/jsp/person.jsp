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
	<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
	</style>
	<script>
		$(function(){
			$('#test').datagrid({
				title:'人员列表',
				iconCls:'icon-save',
				width:870,
				height:533,
				fitColumns: true,
				url:"persons/${depId}",
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'姓名',width:65},
					{field:'sex',title:'性别',width:35},
					{field:'departmentName',title:'所在部门',width:250},
					{field:'code',title:'用户名',width:80},
					{field:'state',title:'状态',width:30},
					{field:'memo',title:'备注',width:140}
				]],
				rownumbers:true,
				pagination:true,
				toolbar:[{
					id:'btnadd',
					text:'添加',
					iconCls:'icon-add',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						window.location.href = "${basePath}toAddPerson/${depId}";
					}
				},{
					id:'btndelete',
					text:'删除',
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
			               url: "deletePerson/${depId}",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		window.location.href = "${basePath}personByDepId/${depId}";
			               }  
	        			});
					}
				},'-',{
					id:'btnedit',
					text:'编辑',
					iconCls:'icon-edit',
					handler:function(){
						$('#btnedit').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						if(rows.length>1){
							alert("不能同时编辑多个人员！");
							return false;
						}
						window.location.href ="${basePath}toEditPerson/${depId}/"+rows[0].id;
					}
				},{
					id:'btnup',
					text:'上调',
					iconCls:'icon-up',
					handler:function(){
						$('#btnup').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						if(rows.length>1){
							alert("不能同时上调多个人员！");
							return false;
						}
						$.ajax({  
			               type: "POST",  
			               url: "upPerson/${depId}/"+rows[0].id,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		window.location.href = "${basePath}personByDepId/${depId}";
			               }  
	        			});
					}
				},{
					id:'btndown',
					text:'下调',
					iconCls:'icon-down',
					handler:function(){
						$('#btndown').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						if(rows.length>1){
							alert("不能同时下调多个人员！");
							return false;
						}
						$.ajax({  
			               type: "POST",  
			               url: "downPerson/${depId}/"+rows[0].id,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		window.location.href = "${basePath}personByDepId/${depId}";
			               }  
	        			});
					}
				},{
					id:'btnauthorize',
					text:'授权角色',
					iconCls:'icon-202',
					handler:function(){
						$('#btnauthorize').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						if(rows.length>1){ 
							alert("不能多选！");
							return false;
						}
						window.parent.location.href = "${basePath}person/toAuthorizeRole/"+rows[0].id;
					}
				},{
					id:'btnresetPassword',
					text:'密码重置',
					iconCls:'icon-112',
					handler:function(){
						$('#btnresetPassword').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "resetPassword",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               }  
	        			});
					}
				}
				]
			});
			var p = $('#test').datagrid('getPager');
			$(p).pagination({
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
