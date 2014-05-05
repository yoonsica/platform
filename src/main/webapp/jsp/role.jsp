<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限角色管理</title>
    
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
			var url = "${basePath}personByRoleId/"+treeNode.id;
			$("#roleInfoFrame").attr("src",url);
		}
		
		function refreshTree(nodeId){
			$.ajax({  
	               type: "POST",  
	               url: "roleManage",
	               async: false,  
	               cache: false,  
	               dataType: "json",
	               success:function(data){
	                   $.fn.zTree.init($("#treeDemo"), setting, data);
	                   if(nodeId!=null){
	                   	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	                       var node = zTree.getNodeByParam("id", nodeId, null);
	                       zTree.selectNode(node);
	                       zTree.expandNode(node, false, false, true);
	                       onClick(event, "treeDemo", node, 1);
	                   }
	               }  
	        	});
		}
		
		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "roleManage",
                async : false,  
                cache : false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodeByParam("id", 0, null);
                    zTree.selectNode(node);
                    zTree.expandNode(node, true, true, true);
                }  
         	});
         	
         	$("#editBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				var url = "${basePath}roleInfo/"+nodeId;
				$("#roleInfoFrame").attr("src",url);
			});
			
			$("#addBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				var url = "${basePath}roleInfo/"+nodeId;
				$("#roleInfoFrame").attr("src",url);
			});
			
			$("#deleteBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				var pnode = nodes[0].getParentNode();
				if(nodes[0].children==undefined){
					$.ajax({  
		                type: "POST",  
		                url: "${basePath}roleRemove/"+nodeId,
		                async : false,  
		                cache:false,  
		                success:function(data){
		                	alert(data);
		                    refreshTree(pnode.id);
		                }  
         			});
				}else{
					alert("含有子节点，不能删除！");
				}
				
	         });
		});
	</script>
  </head>
  
  <body>
    <div class="ceshi" style="position:relative;">
    	<div id="roleMenuDiv" style="width: 320px;position:absolute;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
			<a href="javascript:void(0)" id="addBtn" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加</a>
	  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" >删除</a>
			<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
		</div>
	  	<div id="treeDiv" style="width: 320px;height: 500px;overflow: scroll;position:absolute;top:37px;">
  			<ul id="treeDemo" class="ztree"></ul>
  		</div>
	  	<div id="personDiv" style="position:absolute;left: 400px;height: 700px;width:700px;">
			<div id="roleInfoDiv" style="height: 600px;">
		  		<iframe id="roleInfoFrame" name="roleInfoFrame" src="" frameborder="0" scrolling="no" width="100%" height="600px"></iframe>
		  	</div>
  		</div>
  	</div>
  </body>
</html>
