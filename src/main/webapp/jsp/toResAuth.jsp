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
    
    <title>模块授权管理</title>
    
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
			$("#menuDiv").show();
			if(treeNode.id==1){
				return false;
			}
			var url = "${basePath}toDepAuth/"+treeNode.id;//部门授权情况
			//$("#moduleInfoDiv").load(url);
			$("#moduleInfoFrame").attr("src",url);
		}
		
		function clickZtree(nodeId){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var node = zTree.getNodeByParam("id", nodeId, null);
            zTree.selectNode(node);
            zTree.expandNode(node, true, true, true);
            onClick(event, "treeDemo", node, 1);
		}
		
		function refreshTree(nodeId){
			$.ajax({  
                type: "POST",  
                url: "moduleWithBtnLinkManage",
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
                url: "moduleWithBtnLinkManage",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodeByParam("id", 1, null);
                    zTree.selectNode(node);
                    zTree.expandNode(node, true, true, true);
                }  
         	});
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			$("#depBtn").click(function(){
			//获得当前点击的节点
				var nodes = treeObj.getSelectedNodes();
				$("#moduleInfoFrame").attr("src","${basePath}toDepAuth/"+nodes[0].id);
			});
			$("#personBtn").click(function(){
				var nodes = treeObj.getSelectedNodes();
				$("#moduleInfoFrame").attr("src","${basePath}toPersonAuth/"+nodes[0].id);
			});
			$("#roleBtn").click(function(){
				var nodes = treeObj.getSelectedNodes();
				$("#moduleInfoFrame").attr("src","${basePath}toRoleAuth/"+nodes[0].id);
			});
		});
    </script>
    <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
  
  <body>
  <div class="container">
	  <div class="leftDiv">
		<div class="treeDiv">
	  		<ul id="treeDemo" class="ztree"></ul>
	  	</div>
	  </div>
	<div class="rightDiv">
	  	<div id="menuDiv" style="width:800px;height: 20px;background:#E8F1FF;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
	  		<a href="javascript:void(0)" id="depBtn" class="easyui-linkbutton" plain="true" iconCls="icon-folder" >授权部门</a>
			<a href="javascript:void(0)"  id="personBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >授权人员</a>
			<a href="javascript:void(0)" id="roleBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >授权角色</a>
		</div>
	  	<div id="moduleInfoDiv" style="height: 600px;width:800px;">
	  		<iframe id="moduleInfoFrame" name="moduleInfoFrame" src="" frameborder="0" scrolling="auto" width="900px" height="600px"></iframe>
	  	</div>
  	</div>
  	</div>
  </body>
</html>
