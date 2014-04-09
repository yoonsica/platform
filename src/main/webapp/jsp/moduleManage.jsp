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
			$("#menuDiv").show();
			if (treeNode.isParent) {
				/*var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode);*/
				if(treeNode.children==undefined){
					$("#deleteBtn").show();
				}else{
					$("#deleteBtn").hide();
				}
				$("#addFolder").show();
				$("#addFunction").show();
			}else{
				$("#deleteBtn").show();
				$("#addFolder").hide();
				$("#addFunction").hide();
			}
			if(treeNode.id==1){
				$("#editBtn").hide();
			}else{
				$("#editBtn").show();
			}
			if(treeNode.isFirstNode){
				$("#upBtn").hide();
			}else{
				$("#upBtn").show();
			}
			if(treeNode.isLastNode){
				$("#downBtn").hide();
			}else{
				$("#downBtn").show();
			}
			var url;
			if(treeNode.isParent){
				url = "${basePath}folderInfo/"+treeNode.id;
			}else{
				url = "${basePath}moduleInfo/"+treeNode.id;
			}
			//$("#moduleInfoDiv").load(url);
			$("#moduleInfoFrame").attr("src",url);
		}
		
		var infoTable,moduleEditDiv;
		function test_onload(){
			infoTable = window.frames['moduleInfoFrame'].document.getElementById("infoTable");
			moduleEditDiv = window.frames['moduleInfoFrame'].document.getElementById("moduleEditDiv");
		}
		
		function refreshTree(nodeId){
			$.ajax({  
                type: "POST",  
                url: "moduleManage/1",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    if(nodeId!=null){
                    	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        var node = zTree.getNodeByParam("id", nodeId, null);
                        zTree.selectNode(node);
                        zTree.expandNode(node, true, true, true);
                        onClick(event, "treeDemo", node, 1);
                    }
                }  
         	});
		}

		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "moduleManage/1",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                }  
         	});
			$("#editBtn").click(function(){
				$("#menuDiv").hide();
				$(infoTable).hide();
				$(moduleEditDiv).show();
			});
			$("#upBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var parentNode = nodes[0].getParentNode();
				var nodeId = nodes[0].id;
				$.ajax({  
	                type: "POST",  
	                url: "moduleManage/up/"+nodeId,
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                	refreshTree(nodeId);
	                	if(parentNode.id==1){
	                		alert("更改了菜单项，菜单项将刷新");
	                		window.parent.window.navInit();
	                	}
	                }  
	         	});
			});
			
			$("#downBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var parentNode = nodes[0].getParentNode();
				var nodeId = nodes[0].id;
				$.ajax({  
	                type: "POST",  
	                url: "moduleManage/down/"+nodeId,
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                	refreshTree(nodeId);
	                	if(parentNode.id==1){
	                		alert("更改了菜单项，菜单项将刷新");
	                		window.parent.window.navInit();
	                	}
	                }  
	         	});
			});
			
			$("#addFolder").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				$("#moduleInfoFrame").attr("src","${basePath}moduleManage/toAddFolder/"+nodeId);
			});
			
			$("#addFunction").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				$("#moduleInfoFrame").attr("src","${basePath}moduleManage/toAddFunction/"+nodeId);
			});
			
			$("#deleteBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var parentNode = nodes[0].getParentNode();
				var nodeId = nodes[0].id;
				$.ajax({  
	                type: "POST",  
	                url: "moduleManage/remove/"+nodeId,
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                	refreshTree(parentNode.id);
	                	window.parent.window.refreshWestDiv(null);
	                	if(parentNode.id==1){
	                		alert("更改了菜单项，菜单项将刷新");
	                		window.parent.window.navInit();
	                	}
	                }  
	         	});
			});
			
		});
    </script>
    <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
  
  <body>
  <div class="ceshi" style="position:relative;">
  	<div id="treeDiv" style="height: 500px;width: 200px;position:absolute;">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  	<div style="height: 400px;position:absolute;left: 270px;width:500px;">
	  	<div id="menuDiv" style="display:none;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
	  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" style="display: none;">删除</a>
			<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
			<a href="javascript:void(0)" id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" style="display: none;">上调</a>
			<a href="javascript:void(0)" id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" style="display: none;">下调</a>
			<a href="javascript:void(0)" id="addFolder" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加目录</a>
			<a href="javascript:void(0)" id="addFunction" plain="true" class="easyui-linkbutton" iconCls="icon-addFunction" >添加链接</a>
		</div>
	  	<div id="moduleInfoDiv" style="height: 300px;">
	  		<iframe onload="test_onload()" id="moduleInfoFrame" name="moduleInfoFrame" src="" frameborder="0" scrolling="no" width="100%" ></iframe>
	  	</div>
  	</div>
  	</div>
  </body>
</html>
