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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
		});
    </script>
  </head>
  
  <body>
  	<div id="treeDiv" style="float: left;height: 500px;">
  		<ul id="treeDemo" class="ztree"></ul>
  	</div>
  	<div id="personDiv" style="height: 500px;">
  	</div>
    
  </body>
</html>
