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
    
    <title>人员角色添加</title>
    
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
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
 	<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
 
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
			var url = "${basePath}personByDepIdRoleId/"+treeNode.id+"/${role.id}";
			$("#depInfoFrame").attr("src",url);
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
                }  
         	});
			
		});
    </script>
  </head>
  
  <body>
  <div class="container">
  	<div class="leftDiv" style="width: 320px;">
		<div class="treeDiv" style="width: 320px;">
	  		<ul id="treeDemo" class="ztree"></ul>
	  	</div>
	  </div>
	 <div class="rightDiv" style="width:800px;">
		<div style="width:700px;font-size: 20px;font-weight: bold;text-align: center;">给${role.name }添加人员</div>
		<div id="depInfoDiv" style="height: 600px;width:900px;">
	  		<iframe id="depInfoFrame" name="depInfoFrame" src="" frameborder="0" scrolling="no" width="100%" height="600px"></iframe>
	  	</div>
  	</div>
    </div>
  </body>
</html>
