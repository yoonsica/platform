<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'westDiv.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
		if (treeNode.isParent) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.expandNode(treeNode);
			return false;
		}else{
				window.parent.window.addTab(treeNode.name,treeNode.href,treeNode.iconCls);
		}
	}
		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "${basePath}moduleChild/${moduleId}",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
            		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        var node = zTree.getNodeByParam("id", 2, null);
                        zTree.selectNode(node);
                        zTree.expandNode(node, true, true, true);
                }  
         	});  
		});
    </script>
</head>
  
  <body style="padding: 0;margin: 0;">
    <div id="treeDiv">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  </body>
</html>
