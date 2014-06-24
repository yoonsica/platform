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
    
    <title>编辑角色</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${basePath }static/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/js/common.js"  charset="gb2312"></script>
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
			if(treeNode.id==0){
				return true;
			}
			if(treeNode.getParentNode().id=="${role.id }"){
				alert("不能选则该部门下的子部门！");
				return false;
			}else if(treeNode.id=="${role.id }"){
				alert("不能选则该部门本身！");
				return false;
			}else{
				return true;		
			}
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
			$("#parentId").attr("value", v1);
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
			if("${role.parentId }"==-1){
				$("#parentRow").hide();
			}
			$("#parentSel").focus(function(){
				showMenu();
			});
			$.ajax({  
                type: "POST",  
                url: "roleManage",
                async : false,  
                cache:false,  
                dataType: "json",
                success:function(data){
                    $.fn.zTree.init($("#treeDemo"), setting, data);
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = zTree.getNodeByParam("id","${role.parentId }");
                    $("#parentSel").attr("value",node.name);
                     zTree.selectNode(node);
            			zTree.expandNode(node, false, false, true);
                }  
         	});
			//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		$(function(){
			//这里编码有问题，中文传到后台是乱码
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "roleUpdate",
	                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                data:$("#roleInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	//刷新整个页面
	                	alert("更新成功！");
        		        window.location.href = "${basePath}personByDepId/${role.id}";
	                	window.parent.window.refreshTree(null);//刷新树
	                }  
	         	});
			});
			$("#cancleBtn").click(function(){
               	window.location.href = "${basePath}personByDepId/${role.id }";
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
    
      <div id="roleEditDiv" style="background:#fafafa;padding:10px;">
  		<form id="roleInfoForm" method="post" >
            <input type="hidden" name="id"  value="${role.id }" />
            <input type="hidden" name="isFolder"  value="${role.isFolder }" />
            <table>
            	<tr>
            		<td><label for="name">名称</label></td><td><input  type="text" name="name"  value="${role.name }" style="width: 172px;"/></td>
            	</tr>
            	<tr id="parentRow">
            		<td><label for="parentSel">上级部门</label></td><td><input id="parentSel" type="text" readonly="readonly" value="" style="width: 172px;"/></td>
            	</tr>
            	<tr>
            		<td><div style="line-height: 60px;height: 60px;float:left;">
	          	 			<label for="memo">备注</label>
	        			</div</td>
            		<td><textarea name="memo" style="height: 60px;width: 172px;" > ${role.memo }</textarea></td>
            	</tr>
            	<tr>
            		<td><input type="button" value="确认" id="submitBtn"></td><td><input type="button" value="取消" id="cancleBtn"></td>
            	</tr>
            </table>
	        </div>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
	            <input type="hidden" name="parentId" id="parentId" value="${role.parentId }"></input>
	    </form>
  		
  	</div>
  </body>
</html>
