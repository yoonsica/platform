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
			var url = "${basePath}toResDepAuth/"+treeNode.id;//部门授权情况
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
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodeByParam("id", 1, null);
                    zTree.selectNode(node);
                    zTree.expandNode(node, true, true, true);
                }  
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
  	<div style="height: 620px;position:absolute;left: 270px;width:500px;">
	  	<div id="menuDiv" style="display:none;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
	  		<a href="javascript:void(0)" id="depBtn" class="easyui-linkbutton" plain="true" iconCls="icon-folder" >授权部门</a>
			<a href="javascript:void(0)"  id="personBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >授权人员</a>
			<a href="javascript:void(0)" id="roleBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >授权角色</a>
		</div>
	  	<div id="moduleInfoDiv" style="height: 600px;">
	  		<iframe onload="test_onload()" id="moduleInfoFrame" name="moduleInfoFrame" src="" frameborder="0" scrolling="no" width="100%" height="600px"></iframe>
	  	</div>
  	</div>
  	</div>
  </body>
</html>
