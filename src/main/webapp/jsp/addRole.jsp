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
    
    <title>添加角色</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${basePath }static/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
	<style type="text/css">
body {
	FONT-SIZE: 12px;
	BACKGROUND: #FFFFFF; 
	SCROLLBAR-FACE-COLOR: #bfebd2; 
    SCROLLBAR-HIGHLIGHT-COLOR: #94dc94; 
   SCROLLBAR-SHADOW-COLOR: #ade2c6; 
   SCROLLBAR-3DLIGHT-COLOR: #ade2c6; 
   SCROLLBAR-ARROW-COLOR: #73a790; 
   SCROLLBAR-TRACK-COLOR: #e9efeb; 
   SCROLLBAR-DARKSHADOW-COLOR: #8ac7a4; 
   SCROLLBAR-BASE-COLOR: #168a16; 
  FONT-FAMILY: "����", "Verdana", "Arial";
	margin-top: 0px; 
	margin-left: 2px; 
	margin-right: 0px; 
	overflow-y: auto
}
textarea {
	BORDER-TOP-WIDTH: 1px; 
	BORDER-LEFT-WIDTH: 1px; 
	FONT-SIZE: 12px; 
	BORDER-BOTTOM-WIDTH: 1px; 
	BORDER-RIGHT-WIDTH: 1px
}
td {
	FONT-SIZE: 12px;
	padding: 3px;
	font-size: 12px;
}
tr{
	background-color: #ffffff;
}
#depEditDiv div{
	margin-bottom: 5px;
}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#cancleBtn").click(function(){
               	window.location.href = "${basePath}personByRoleId/${parentId }";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "addRole",
	                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                data:$("#roleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	//刷新整个页面
	                	alert("添加成功！"+data);
        		        window.location.href = "${basePath}personByRoleId/"+data;
	                	window.parent.window.refreshTree(data);//刷新树
	                }  
	         	});
			});
		})
			
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
    
      <div id="roleEditDiv" style="background:#fafafa;padding:10px;">
  		<form id="roleInfoForm" method="post" >
            <table>
            	<tr>
            		<td><label for="name">名称</label></td><td><input  type="text" name="name" style="width: 172px;"/></td>
            	</tr>
            	<tr>
            		<td><label for="parentSel">父节点</label></td><td><input id="parentSel" type="text" readonly="readonly" value="${parentName }" style="width: 172px;"/></td>
            	</tr>
            	<tr>
            		<td>类别</td><td><input type="radio" name="isFolder" value="1" />目录<input type="radio" name="isFolder" value="0" checked="checked"/>角色</td>
            	</tr>
            	<tr>
            		<td><div style="line-height: 60px;height: 60px;float:left;">
	          	 			<label for="memo">备注</label>
	        			</div</td>
            		<td><textarea name="memo" style="height: 60px;width: 172px;" ></textarea></td>
            	</tr>
            	<tr>
            		<td><input type="button" value="确认" id="submitBtn"></td><td><input type="button" value="取消" id="cancleBtn"></td>
            	</tr>
            </table>
	        </div>
	            <input type="hidden" name="parentId" id="parentId" value="${parentId }"></input>
	    </form>
  		
  	</div>
  </body>
</html>
