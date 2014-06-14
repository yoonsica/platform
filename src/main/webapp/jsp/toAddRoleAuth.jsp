<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>授予角色</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
  
  	<script type="text/javascript">
	  	var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
	   				beforeClick: beforeClick,
	   				onClick: onClick
	   			}
			};
	
		function beforeClick(treeId, treeNode, clickFlag) {
		}
		
		function onClick(event, treeId, treeNode, clickFlag) {
			//var url = "${basePath}personByDepIdRoleId/"+treeNode.id+"/${role.id}";
			//$("#depInfoFrame").attr("src",url);
			if(treeNode.isParent){
				alert("请选择角色，不要选择目录");
				return false;
			}
			$('#test').datagrid('appendRow',{
				id: treeNode.id,
				name:treeNode.name
			});
		}
		
		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "roleManage",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                }  
         	});
			
			$('#test').datagrid({
				title:'已选角色列表',
				iconCls:'icon-save',
				width:600,
				height:'auto',
				fitColumns: true,
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'角色名称',width:80}
				]],
				rownumbers:true,
				toolbar:[{
					id:'btnDelete',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						if(!rows.length>0){
							alert("请选择要删除的角色");
							return false;
						}
						for(var i=0; i<rows.length; i++){
						   	$('#test').datagrid('deleteRow',$('#test').datagrid('getRowIndex',rows[i]));
						}
					}
				},'-',{
					id:'btnConfirm',
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						$('#test').datagrid('selectAll');//全选
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			                type: "POST",  
			                url: "addResAuth",
			                data:"idArray="+idArray+"&resId=${resId}&accessType=0",
			                async : false,  
			                cache:false,  
			                success:function(data){
			                	alert(data);
			                	window.location.href = "${basePath}toResAuth/${resId}/0";
			                }  
			         	});
					}
				},{
					id:'btnBack',
					text:'返回资源分配',
					iconCls:'icon-undo',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						window.location.href = "${basePath}toResAuth/${resId}/0";
					}
				}]
			});
		});
    </script>
  </head>
  
  <body>
  <div class="container">
 	<div class="leftDiv" style="overflow:auto;">
	  	<div id="treeDiv">
	  		<ul id="treeDemo" class="ztree"></ul>
	  	</div>
  	</div>
  	<div class="rightDiv">
		<div id="depInfoDiv">
		<table id="test"></table>
	  	</div>
  	</div>
    </div>
  </body>
</html>
