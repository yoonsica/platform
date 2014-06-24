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
			<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
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
			var url;
			if(treeNode.isParent){
				url = "";
			}
			url = "${basePath}personByRoleId/"+treeNode.id;
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
	                       zTree.selectNode(node,false);
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
                    
                     var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					$("#depBtn").click(function(){
					//获得当前点击的节点
						var nodes = treeObj.getSelectedNodes();
						$("#roleInfoFrame").attr("src","${basePath}depByRoleId/"+nodes[0].id);
					});
					$("#personBtn").click(function(){
						var nodes = treeObj.getSelectedNodes();
						$("#roleInfoFrame").attr("src","${basePath}personByRoleId/"+nodes[0].id);
					});
					
					zTree.selectNode(node);
                    zTree.expandNode(node, true, true, true);
                    onClick(event, "treeDemo", node, 1);
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
				if(!nodes[0].isParent){
					alert("请选择目录节点作为父节点!");
					return false;
				}
				var url = "${basePath}toAddRole/"+nodeId;
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
    <div class="container">
    	<div class="leftDiv" style="width: 330px;">
	    	<div class="leftMenuDiv" >
				<a href="javascript:void(0)" id="addBtn" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加</a>
		  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" >删除</a>
				<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
			</div>
		  	<div class="treeDiv" style="width: 330px;">
	  			<ul id="treeDemo" class="ztree" ></ul>
	  		</div>
  		</div>
	  	<div class="rightDiv">
		  	<div id="menuDiv" style="width:870px;height: 20px;background:#E8F1FF;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
		  		<a href="javascript:void(0)" id="depBtn" class="easyui-linkbutton" plain="true" iconCls="icon-129" >授权部门</a>
				<a href="javascript:void(0)"  id="personBtn" plain="true" class="easyui-linkbutton" iconCls="icon-202" >授权人员</a>
			</div>
	  		<iframe id="roleInfoFrame" name="roleInfoFrame" src="" frameborder="0" scrolling="auto" width="894px" height="700px"></iframe>
  		</div>
  	</div>
  </body>
</html>
