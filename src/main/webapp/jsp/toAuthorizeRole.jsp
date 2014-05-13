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
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
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
						$('#test').datagrid('selectAll');//获得选中行
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			                type: "POST",  
			                url: "authorizeRole",
			                data:"roleIds="+idArray+"&personIds="+$("#personIds").text(),
			                async : false,  
			                cache:false,  
			                success:function(data){
			                	alert(data);
			                	window.location.href = "${basePath}jsp/departmentManager.jsp";
			                }  
			         	});
					}
				}]
			});
		});
    </script>
  </head>
  
  <body>
  <div class="ceshi" style="position:relative;">
  	<div id="treeDiv" style="width: 300px;height: 500px;overflow: scroll;position:absolute;top:5px;">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  	<div id="personDiv" style="position:absolute;left: 400px;height: 700px;width:700px;">
		<div style="width:700px;font-size: 20px;font-weight: bold;text-align: center;">
			为<c:forEach items="${personList }" var="person" end="3">
				${person.name }、	
			</c:forEach>等${fn:length(personList) }个人选择角色
			<span id="personIds" style="display: none;"><c:forEach items="${personList }" var="person">${person.id },</c:forEach></span>
		</div>
		<div id="depInfoDiv" style="height: 600px;width:700px;float:left;">
		<table id="test"></table>
	  	</div>
  	</div>
    </div>
  </body>
</html>
