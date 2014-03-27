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
			if (treeNode.isParent) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode);
				$("#deleteBtn").hide();
			}else{
				$("#deleteBtn").show();
			}
			if(treeNode.id==1){
				$("#menuDiv").hide();
			}else{
				$("#menuDiv").show();
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
			var url = "${basePath}moduleInfo/"+treeNode.id;
			//$("#moduleInfoDiv").load(url);
			$("#moduleInfoFrame").attr("src",url);
		}
		
		var infoTable,moduleEditDiv;
		function test_onload(){
			infoTable = window.frames['moduleInfoFrame'].document.getElementById("infoTable");
			moduleEditDiv = window.frames['moduleInfoFrame'].document.getElementById("moduleEditDiv");
		}

		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "moduleManage/1",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                	var newCount = data.count;
                    $.fn.zTree.init($("#treeDemo"), setting, data.childList);
                }  
         	});
			$("#editBtn").click(function(){
				$("#menuDiv").hide();
				$(infoTable).hide();
				$(moduleEditDiv).show();
			});
		});
    </script>
    <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
  
  <body>
  	<div id="treeDiv" style="float: left;height: 500px;">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  	<div id="menuDiv" style="background:#C9EDCC;padding:5px;width:600px;margin-left:250px;display: none;">
  		<a href="#" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" style="display: none;">删除</a>
		<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
		<a href="javascript:void(0)" id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" style="display: none;">上调</a>
		<a href="javascript:void(0)" id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" style="display: none;">下调</a>
	</div>
  	<div id="moduleInfoDiv" style="height: 500px;margin-left:250px;">
  		<iframe onload="test_onload()" id="moduleInfoFrame" name="moduleInfoFrame" src="" frameborder="0" height="500px" scrolling="no" width="600px"></iframe>
  	</div>
  </body>
</html>
