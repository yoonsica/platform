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
    
    <title>模块信息</title>
    
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
#moduleEditDiv div{
	margin-bottom: 5px;
}
	</style>
	<script type="text/javascript">
	var setting = {
			view: {
				dblClickExpand: false
			},
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

		function beforeClick(treeId, treeNode) {
			var check = (treeNode && !treeNode.isParent);
			if (check) alert("链接节点不能作为父节点...");
			return !check;
		}
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			v = "";
			v1 = "";
			v = nodes[0].name;
			v1 = nodes[0].id;
			var cityObj = $("#parentSel");
			cityObj.attr("value", v);
			$("#parent").attr("value", v1);
		}

		function showMenu() {
			var cityObj = $("#parentSel");
			var cityOffset = $("#parentSel").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		$(document).ready(function(){
			$("#parentSel").focus(function(){
				showMenu();
			});
			$.ajax({  
                type: "POST",  
                url: "moduleManage/1",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodeByTId("${moduleInfo.parent }");
                    $("#parentSel").attr("value",node.name);
                    var node = zTree.getNodeByParam("id", "${moduleInfo.parent }", null);
                    zTree.selectNode(node);
                    zTree.expandNode(node, false, false, true);
                }  
         	});
			//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		$(function(){
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "moduleUpdate",
	                data:$("#moduleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	//刷新整个页面
	                	alert("更新成功！");
	                	$(window.parent.document.getElementById("menuDiv")).show();
	                	$("#moduleEditDiv").hide();
	                	$("#infoTable").show();
	                	window.location.reload(true);
	                	window.parent.window.refreshTree(null);//刷新树
	                }  
	         	});
			});
			$("#cancleBtn").click(function(){
               	$(window.parent.document.getElementById("menuDiv")).show();
               	$("#moduleEditDiv").hide();
               	$("#infoTable").show();
               	//window.location.reload(true);
               	//window.parent.window.refreshTree(null);//刷新树
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
    <table border="0" bgcolor="#cccccc" cellspacing="1" cellpadding="5" width="100%" id="infoTable" class="toptablebg">
    	<thead>
    		<tr><td colspan="2" align="center" style="font-weight: bolder;">当前选中模块信息</td></tr>
    	</thead>
    	<tbody>
    		<tr >
    			<td align="center" width="20%">ID</td><td width="80%">${moduleInfo.id }</td>
    		</tr>
    		<tr>
    			<td align="center" width="20%">名称</td><td width="80%">${moduleInfo.name }</td>
    		</tr>
    		<tr>
    			<td align="center" width="20%">类别</td><td width="80%">${moduleInfo.parentName }</td>
    		</tr>
    		<tr>
    			<td align="center" width="20%">链接</td><td width="80%">${moduleInfo.link }</td>
    		</tr>
    		<tr>
    			<td align="center" width="20%">状态</td><td width="80%">${moduleInfo.state }</td>
    		</tr>
    	</tbody>
    </table>
    
      <div id="moduleEditDiv" style="display:none;background:#fafafa;padding:10px;">
  		<form id="moduleInfoForm" method="post" >
            <input type="hidden" name="id"  value="${moduleInfo.id }" ></input>
	        <div>
	            <label for="name">名称</label>
	            <input  type="text" name="name"  value="${moduleInfo.name }" style="width: 172px;"></input>
	        </div>
	        <div >
	            <label for="parent">类别</label>
				<input id="parentSel" type="text" readonly="readonly" value="" style="width: 172px;"/>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
	            <input type="hidden" name="parent" id="parent" value="${moduleInfo.parent }"></input>
	        </div>
	        <div style="line-height: 60px;height: 60px;float:left;">
	          	 <label for="link">链接</label>
	        </div>
	        <div style="margin-left: 3px;float: left;">
	        	<textarea name="link" style="height: 60px;width: 172px;">${moduleInfo.link }</textarea>
	        </div>
	        <div style="clear: left;">
	            <label for="state">状态</label>
	            <input type="radio" name="state"  value="1" <c:if test="${moduleInfo.state=='启用' }">checked="checked"</c:if>>启用
	            <input type="radio" name="state"  value="0" <c:if test="${moduleInfo.state=='停用' }">checked="checked"</c:if>>停用
	        </div>
	        <div style="height: 250px;">
	            <label for="icon">功能图标</label><br>
	            <c:forEach items="${iconList }" var="icon" varStatus="status">
	            	<c:choose>
	            		<c:when test="${moduleInfo.icon == icon}">
	            			<div style="line-height: 18px;float:left;margin-right:10px;height: 18px;width: 50px;">
		            		<input name="icon" type="radio" value="${icon}" checked="checked"/>
							<img src="static/images/icons/${icon}" width="18" height="18" style="margin-left: -5px;"/>
							</div>
							
						</c:when>
						<c:otherwise>
						<div style="line-height: 18px;float:left;margin-right:10px;height: 18px;width: 50px;">
							<input name="icon" type="radio" value="${icon}" />
							<img src="static/images/icons/${icon}" width="18" height="18" style="margin-left: -5px;"/>
						</div>
						</c:otherwise>
					</c:choose>
	            </c:forEach>
	        </div>
	        <div style="clear: left;margin-left: auto;margin-right: auto;TEXT-ALIGN: center;">
	            <input type="button" value="确认" id="submitBtn">
	            <input type="button" value="取消" id="cancleBtn">
	        </div>
	    </form>
  		
  	</div>
  </body>
</html>
