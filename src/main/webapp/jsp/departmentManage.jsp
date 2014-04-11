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
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
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
			return false;
		}else{
			var url = "${basePath}department/"+treeNode.id;
			$("#personDiv").load(url);
		}
	}

		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "department",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
            		//$("#rightDiv").load("http://localhost:8081/device/ztree/bdz");
                }  
         	});
         	$("#addFolder").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				var nodeId = nodes[0].id;
				$("#depInfoFrame").attr("src","${basePath}depManage/toAddDepartment/"+nodeId);
			});
		});
    </script>
  </head>
  
  <body>
  <div class="ceshi" style="position:relative;">
  <div id="depMenuDiv" style="width: 300px;position:absolute;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
		<a href="javascript:void(0)" id="addFolder" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加</a>
  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" >删除</a>
		<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
		<a href="javascript:void(0)" id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >上调</a>
		<a href="javascript:void(0)" id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" >下调</a>
	</div>
  	<div id="treeDiv" style="width: 300px;height: 500px;overflow: scroll;position:absolute;top:30px;">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  	<div id="personDiv" style="position:absolute;left: 400px;height: 500px;border:1px solid red;">
  		<div id="personMenuDiv" style="height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
		<a href="javascript:void(0)" id="addFolder" plain="true" class="easyui-linkbutton" iconCls="icon-addFolder" >添加</a>
  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" >删除</a>
		<a href="javascript:void(0)" id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
		<a href="javascript:void(0)" id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >上调</a>
		<a href="javascript:void(0)" id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" >下调</a>
		</div>
		<div id="depInfoDiv" style="height: 300px;">
	  		<iframe onload="test_onload()" id="depInfoFrame" name="depInfoFrame" src="" frameborder="0" scrolling="no" width="100%" ></iframe>
	  	</div>
  	</div>
    </div>
  </body>
</html>
