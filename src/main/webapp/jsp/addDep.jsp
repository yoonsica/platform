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
    
    <title>添加部门</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
	
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
#moduleEditDiv div{
	margin-bottom: 5px;
}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#cancleBtn").click(function(){
				window.location.href = "${basePath}personByDepId/${parentId }";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "depManage/addDep",
	                data:$("#depInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert("添加成功！");
	                	//window.location.href = "${basePath}personByDepId/${parentId }";
	                	window.parent.window.refreshTree(data);
	                }  
	         	});
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
      <div id="depEditDiv" style="background:#fafafa;padding:10px;">
  		<form id="depInfoForm" method="post" >
  		<input type="hidden" name="parentId" value="${parentId }" readonly="readonly"/>
  			<table>
  				<tr>
  					<td><label for="parentName">上级部门名称</label></td><td><input type="text" name="parentName" value="${parentName }" disabled="disabled"/></td>
  				</tr>
  				<tr>
  					<td><label for="name">名称</label></td><td><input type="text" name="name"/></td>
  				</tr>
  				<tr>
  					<td>
  						<div style="line-height: 60px;height: 60px;float:left;">
	          	 			<label for="memo">备注</label>
	        			</div>
	        		</td>
	        		<td><textarea name="memo" style="height: 60px;"></textarea></td>
  				</tr>
  			</table>
	        <div style="margin-left: 80px;margin-top: 10px;">
	            <input type="button" value="确认" id="submitBtn">
	            <input type="button" value="取消" id="cancleBtn">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
