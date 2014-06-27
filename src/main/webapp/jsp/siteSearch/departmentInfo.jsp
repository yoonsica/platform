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
    
    <title>部门信息</title>
    
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
	
	input { width: 256px; }
	
	form { margin: 10px 0; }
	
    .base-div { 
    	width: 1000px; 
    	height: 500px; 
    	left: 50%; 
    	margin: 0 0 0 -500px; 
    	position: absolute; 
    }
    
    .tree-div {
    	width: 360px;
    	height: 500px;
    	overflow: auto;
    	float: left;
    }
    
    .relation-div {
    	width: 640px;
    	height: 500px;
    	float: right;
    }
	</style>
  	<script type="text/javascript">
  	
	  	function getQueryParams(id) {
	  		return { id: id, type: "department" };
	  	}
	  	
	  	var setting = {
        	view: { selectedMulti: false }, 
			data: { simpleData: { enable: true } }, 
			callback: {
   				onClick: function (event, treeId, treeNode, clickFlag) {
   					$('#personTable').datagrid('load', getQueryParams(treeNode.id ));
   					$('#resourceTable').datagrid('load', getQueryParams(treeNode.id));
   				}
			}
		};
	  	
	  	function initDepartmentTree(nodeId) {
	  		$.ajax({  
                type: "POST",  
                url: "${basePath}SiteSearch/getDepartmentTree",
                success: function(departmentList) {
                	$.fn.zTree.init($("#tree"), setting, departmentList);
                	var zTree = $.fn.zTree.getZTreeObj("tree");
                	
                 	// 未指定节点ID时默认为根节点
                 	var node = null;
                    if(nodeId != null){
                        node = zTree.getNodeByParam("id", nodeId, null);
                    } else {
                    	node = zTree.getNodeByParam("id", 0, null);
                    }
                    
                    zTree.selectNode(node);
                    zTree.setting.callback.onClick(event, "tree", node, 1);
                    zTree.expandNode(node, true, true, true);
                }  
         	});
	  	}
	  	
		$(document).ready(function(){
			initDepartmentTree(${departmentId });
			
			$('#personTable').datagrid({
				title: "人员列表",
				width: 640, 
				height: 335,
				url: "${basePath}SiteSearch/getPersons", 
				queryParams: getQueryParams(${departmentId }), 
				singleSelect: true, 
				columns:[[
					{field:'id',title:'id',width:50,hidden:true},
					{field:'name',title:'姓名',width:100},
					{field:'sex',title:'性别',width:50},
					{field:'departmentName',title:'所在部门',width:100},
					{field:'code',title:'用户名',width:150},
					{field:'state',title:'状态',width:50},
					{field:'memo',title:'备注',width:180}
				]],
				pagination:true, 
				onClickRow: function(rowIndex, rowData) {
		    		window.location.href = "${basePath}SiteSearch/showPersonInfo?id=" + rowData.id;
			    }
			});
			
			$('#resourceTable').datagrid({
				title: "资源列表",
				width: 640,
				height: 165,
				url: "${basePath}SiteSearch/getResources", 
				queryParams: getQueryParams(${departmentId }), 
				singleSelect: true, 
				columns:[[
					{field:'id',title:'id',hidden: true},
					{field:'name',title:'名称',width: 200},
					{field:'memo',title:'备注',width: 300}, 
					{field:'state',title:'状态',width: 100}
				]],
				pagination:true, 
				onClickRow: function(rowIndex, rowData) {
		    		window.location.href = "${basePath}SiteSearch/showResourceInfo?id=" + rowData.id;
			    }
			});
		});
    </script>
  </head>
  
  <body>
	<div class="base-div">
		<div id="treeDiv" class="tree-div">
			<ul id="tree" class="ztree"></ul>
		</div>
		<div class="relation-div" >
			<table id="personTable"></table>
		    <table id="resourceTable"></table>
		</div>
    </div>
  </body>
</html>
