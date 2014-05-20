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
    
    <title>编辑人员</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${basePath }static/ztree/css/demo.css" type="text/css"><!-- 不加这个下拉树会变透明 -->
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
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
			if(treeNode.id==4){return true};
			if(treeNode.getParentNode().id=="${department.id }"){
				alert("不能选则该部门下的子部门！");
				return false;
			}else if(treeNode.id=="${department.id }"){
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
			$("#depId").attr("value", v1);
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


		$(function(){
				$("#parentSel").focus(function(){
					showMenu();
				});
				$.ajax({  
	                type: "POST",  
	                url: "department",
	                async : false,  
	                cache:false,  
	                dataType: "json",
	                success:function(data){
	                    $.fn.zTree.init($("#treeDemo"), setting, data);
	                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	                    var node = zTree.getNodeByParam("id","${department.id}");
	                    $("#parentSel").attr("value",node.name);
	                   	zTree.selectNode(node);
	            		zTree.expandNode(node, false, false, true);
	                }  
	         	});
			$("#cancelBtn").click(function(){
				window.location.href = "${basePath}personByDepId/${department.id }";
			});
			$("#submitBtn").click(function(){
				$.ajax({  
	                type: "POST",  
	                url: "editPerson",
	                data:$("#personInfoForm").serialize(),
	                async : false,  
	                cache:false,  
	                success:function(data){
	                	alert(data);
	                	window.location.href = "${basePath}personByDepId/${department.id }";
	                }  
	         	});
			});
		})
	</script>
  </head>
  <body style="margin: 0;padding: 0;">
      <div id="depEditDiv" style="background:#fafafa;padding:10px;">
  		<form id="personInfoForm" method="post" >
  		<input type="hidden" name="depId" id="depId" value="${department.id }" />
  		<input type="hidden" name="personId" id="personId" value="${person.id }" />
  			<table>
  				<tr id="parentRow">
            		<td><label for="parentSel">所属部门</label></td>
            		<td>
            			<input id="parentSel" type="text" readonly="readonly" value="${department.name }" style="width: 173px;"/>
            			<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
						<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
					</div>
            		</td>
            	</tr>
  				<tr>
  					<td><label for="name">姓名</label></td><td><input type="text" name="name" value="${person.name }" style="width: 173px;"/></td>
  				</tr>
  				<tr>
  					<td><label for="code">用户名</label></td><td><input type="text" name="code"  value="${person.code }" style="width: 173px;"/></td>
  				</tr>
  				<tr>
  					<td><label for="sex">性别</label></td><td><input type="radio" name="sex" <c:if test="${person.sex=='1' }">checked="checked"</c:if>  value="1"/>男 <input type="radio" name="sex"  value="0" <c:if test="${person.sex=='0' }">checked="checked"</c:if> />女  </td>
  				</tr>
  				<tr>
  					<td><label for="state">状态</label></td><td><input type="radio" name="state" checked="checked" value="0" <c:if test="${person.state=='0' }">checked="checked"</c:if> />正常 <input type="radio" name="sex" value="1" <c:if test="${person.state=='1' }">checked="checked"</c:if> />停用  </td>
  				</tr>
  				<tr>
  					<td>
  						<div style="line-height: 60px;height: 60px;float:left;width: 173px;">
	          	 			<label for="memo">备注</label>
	        			</div>
	        		</td>
	        		<td><textarea name="memo" style="height: 60px;"></textarea></td>
  				</tr>
  				<tr>
  					<td colspan="2" style="text-align: center;"><input type="button" value="确认" id="submitBtn">
	            <input type="button" value="取消" id="cancelBtn"></td>
  				</tr>
  			</table>
	    </form>
  		
  	</div>
  </body>
</html>
