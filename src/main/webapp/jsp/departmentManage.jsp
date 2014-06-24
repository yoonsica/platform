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
    
    <title>部门人员管理</title>
    
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
			var url = "${basePath}personByDepId/"+treeNode.id;
			$("#depInfoFrame").attr("src",url);
		}
		
		function refreshTree(nodeId){
			$.ajax({  
	               type: "POST",  
	               url: "department",
	               async: false,  
	               cache: false,  
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
                url: "${basePath}department",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	                var node = zTree.getNodeByParam("id", 4, null);
	                zTree.selectNode(node);
                    onClick(event, "treeDemo", node, 1);
                }  
         	});
         	$("#addFolder").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				$("#depInfoFrame").attr("src","${basePath}depManage/toAddDepartment/"+nodeId);
			});
			
			$("#deleteBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				var pnode = nodes[0].getParentNode();
				if(nodes[0].children==undefined){
					$.ajax({  
		                type: "POST",  
		                url: "${basePath}depManage/remove/"+nodeId,
		                async : false,  
		                cache:false,  
		                success:function(data){
		                	alert(data);
		                    refreshTree(pnode.id);
		                }  
         			});
				}else{
					alert("含有子部门，不能删除！");
				}
				
	         });
			$("#editBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				var url = "${basePath}depInfo/"+nodeId;
				$("#depInfoFrame").attr("src",url);
			});
			
			$("#upBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				if(nodes[0].isFirstNode){
					alert("已经是第一个节点了！");
					return false;
				}
				var nodeId = nodes[0].id;
				$.ajax({  
	                type: "POST",  
	                url: "${basePath}depManage/up/"+nodeId,
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                    refreshTree(nodeId);
	                }  
         		});
			});
			
			$("#downBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				if(nodes[0].isLastNode){
					alert("已经是最后一个节点了！");
					return false;
				}
				var nodeId = nodes[0].id;
				$.ajax({  
	                type: "POST",  
	                url: "${basePath}depManage/down/"+nodeId,
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                    refreshTree(nodeId);
	                }  
         		});
			});
		});
    </script>
  </head>
  
  <body>
  	<div class="container">
  		<div class="leftDiv" style="width: 340px;">
	  		 <div class="leftMenuDiv">
				<a href="javascript:void(0)" id="addFolder" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加</a>
		  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" >删除</a>
				<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
				<a href="javascript:void(0)" id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >上调</a>
				<a href="javascript:void(0)" id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" >下调</a>
			</div>
		  	<div class="treeDiv">
		  		<ul id="treeDemo" class="ztree"></ul>
		  	</div>
	  	</div>
	  	 
	  	<div class="rightDiv" style="width: 859px;">
  			<iframe id="depInfoFrame" name="depInfoFrame" src="" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
	  	</div>
  	</div>
  </body>
</html>
