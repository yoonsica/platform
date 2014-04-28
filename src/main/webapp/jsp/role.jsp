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
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript">
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
                    var node = zTree.getNodeByParam("id", 1, null);
                    zTree.selectNode(node);
                    zTree.expandNode(node, true, true, true);
                }  
         	});
		});
	</script>
  </head>
  
  <body>
    <div class="ceshi" style="position:relative;">
  	<div id="treeDiv" style="height: 500px;width: 200px;position:absolute;">
  		<ul id="treeDemo" class="ztree">
  		<!-- 显示角色树 -->
  		</ul>
  	</div>
  	<div style="height: 620px;position:absolute;left: 270px;width:500px;">
	  	<div id="menuDiv" style="display:none;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
	  		<a href="javascript:void(0)" id="deleteBtn" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" style="display: none;">删除</a>
			<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
			<a href="javascript:void(0)" id="addBtn" plain="true" class="easyui-linkbutton" iconCls="icon-addFunction" >添加角色</a>
		</div>
	  	<div id="moduleInfoDiv" style="height: 600px;">
	  		<iframe onload="test_onload()" id="moduleInfoFrame" name="moduleInfoFrame" src="" frameborder="0" scrolling="no" width="100%" height="600px"></iframe>
	  	</div>
  	</div>
  	</div>
  </body>
</html>
