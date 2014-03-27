<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'moduleInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
	table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;}
th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;}
th{font-weight:bold;background:#ccc;}
label{
			width:120px;
			display:block;
		}
		input{
			padding:0;margin:0;
		}
		image{
			padding:0;
			margin:0;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#submitBtn").click(function(){
				alert("submit"+$("#moduleInfoForm").serialize());
				$.ajax({  
	                type: "POST",  
	                url: "moduleUpdate",
	                data:$("#moduleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                dataType: "json",
	                success:function(data){
	                	alert(data);
	                	$(window.parent.document.getElementById("menuDiv")).show();
	                }  
	         	});
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
    <table border="1" width="600px" id="infoTable">
    	<thead>
    		<tr><td colspan="2">当前选中模块信息</td></tr>
    	</thead>
    	<tbody>
    		<tr>
    			<td>ID</td><td>${moduleInfo.id }</td>
    		</tr>
    		<tr>
    			<td>名称</td><td>${moduleInfo.name }</td>
    		</tr>
    		<tr>
    			<td>类别</td><td>${moduleInfo.parent }</td>
    		</tr>
    		<tr>
    			<td>链接</td><td>${moduleInfo.link }</td>
    		</tr>
    		<tr>
    			<td>状态</td><td>${moduleInfo.state }</td>
    		</tr>
    	</tbody>
    </table>
    
      <div id="moduleEditDiv" style="display:none;height: 500px;background:#fafafa;padding:10px;">
  		<form id="moduleInfoForm" method="post"  novalidate>
            <input class="easyui-validatebox" type="hidden" name="id" required="true" value="${moduleInfo.id }" ></input>
	        <div>
	            <label for="name">名称:</label>
	            <input class="easyui-validatebox" type="text" name="name" required="true" value="${moduleInfo.name }"></input>
	        </div>
	        <div>
	            <label for="parent">类别</label>
	            <input class="easyui-validatebox" type="text" name="parent" required="true" value="${moduleInfo.parent }"></input>
	        </div>
	        <div>
	            <label for="link">链接</label>
	            <textarea name="link" style="height:60px;">${moduleInfo.link }</textarea>
	        </div>
	        <div>
	            <label for="state">状态</label>
	            <input type="radio" name="state"  value="1" <c:if test="${moduleInfo.state=='1' }">checked="checked"</c:if>>启用
	            <input type="radio" name="state"  value="0" <c:if test="${moduleInfo.state=='0' }">checked="checked"</c:if>>停用
	        </div>
	        <div style="float: left;">
	            <label for="icon">功能图标</label>
	            <c:forEach items="${iconList }" var="icon">
	            	<c:choose>
	            		<c:when test="${moduleInfo.icon == icon}">
	            			<div style="margin-right: 115px;float:left;">
		            		<input name="icon" type="radio" value="${icon}" checked="checked"/>
							<img src="static/images/icons/${icon}" width="18" height="18" />
							</div>
						</c:when>
						<c:otherwise>
						<div style="margin-right: 15px;float:left;line-height: 18;">
							<input name="icon" type="radio" value="${icon}" />
							<img src="static/images/icons/${icon}" width="18" height="18" />
						</div>
						</c:otherwise>
					</c:choose>
	            </c:forEach>
	        </div>
	        <div>
	            <input type="button" value="确认" id="submitBtn">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
