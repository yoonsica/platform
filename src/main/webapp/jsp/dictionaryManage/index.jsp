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
    
    <title>系统模块管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
	<link rel="stylesheet" href="${basePath }static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.core-3.5.js"></script>
  	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${basePath }static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
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
	td {
		FONT-SIZE: 12px;
		padding: 3px;
		font-size: 12px;
	}
	tr{
		background-color: #ffffff;
		height: 36px;
	}
	</style>
  	<script type="text/javascript">
  		var setting = {
        	view: {  
                selectedMulti: false        //禁止多点选中  
            }, 
			data: {
				simpleData: { enable: true }
			},
			callback: {
   				onClick: function (event, treeId, treeNode, clickFlag) {
   					// 设置按钮显示与否
   					if(treeNode.isParent == false) {
   						$("#addBtn").hide();
   					} else {
   						$("#addBtn").show();
   					}
   					if(treeNode.level == 0){
   						$("#editBtn").hide();
   						$("#deleteBtn").hide();
   					}else{
   						$("#editBtn").show();
   						$("#deleteBtn").show();
   					}
   					if(treeNode.isFirstNode){
   						$("#upBtn").hide();
   					}else{
   						$("#upBtn").show();
   					}
   					if(treeNode.isLastNode){
   						$("#downBtn").hide();
   					}else{
   						$("#downBtn").show();
   					}
   					
   					// 显示选中节点的参数信息
   					showInfoDiv(treeNode);
   				}
   			}
		};
  		
		function refreshTree(nodeId){
			$.ajax({  
                type: "POST",  
                url: "dictionaryManage/show",
                async: true,  
                cache: false, 
                success:function(dictionaryList){
                	// 初始化字典树
                	$.fn.zTree.init($("#treeDemo"), setting, dictionaryList);
                	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                	
                 	// 未指定节点ID时默认为根节点
                 	var node = null;
                    if(nodeId != null){
                        node = zTree.getNodeByParam("id", nodeId, null);
                    } else {
                    	node = zTree.getNodeByParam("id", 0, null);
                    }
                    
                    zTree.selectNode(node);
                    zTree.setting.callback.onClick(event, "treeDemo", node, 1);
                    zTree.expandNode(node, true, true, true);
                }  
         	});
		}
		
		function splitNodeValue(node) {
			var nameValue = node.name.substring(0, node.name.length - 1).split("(");
			return nameValue;
		}
		
		function showInfoDiv(node) {
			$("#infoDiv").show();
			$("#addDiv").hide();
			$("#editDiv").hide();
			
			$("#infoDiv [name='name']").text(splitNodeValue(node)[0]);
			$("#infoDiv [name='value']").text(splitNodeValue(node)[1]);
		}
		
		function showAddDiv() {
			$("#infoDiv").hide();
			$("#addDiv").show();
			$("#editDiv").hide();
			
			$("#addDiv [name='name']").attr("value", "");
			$("#addDiv [name='value']").attr("value", "");
		}
		
		function showEditDiv(node) {
			$("#infoDiv").hide();
			$("#addDiv").hide();
			$("#editDiv").show();
			
			$("#editDiv [name='name']").attr("value", splitNodeValue(node)[0]);
			$("#editDiv [name='value']").attr("value", splitNodeValue(node)[1]);
		}
		

		$(document).ready(function(){
			refreshTree();
			
			$("#addBtn").click(function(){
				showAddDiv();
			});
			
			$("#editBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				showEditDiv(selectedNode);
			});
			
			$("#deleteBtn").click(function(){
				if(!confirm("你确认要删除吗？"))
					return;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				var parentNode = selectedNode.getParentNode();
				$.ajax({  
	                type: "POST",  
	                url: "dictionaryManage/delete",
	                async: true,  
	                cache: false, 
	                dataType: "json", 
	                data: { id: selectedNode.id }, 
	                success:function() {
	                	refreshTree(parentNode.id);
	                }  
	         	});
			});
			
			$("#upBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				var parentNode = selectedNode.getParentNode();
				var prevNode = selectedNode.getPreNode();
				$.ajax({  
	                type: "POST",  
	                url: "dictionaryManage/up",
	                async: true,  
	                cache: false, 
	                dataType: "json", 
	                data: { currentId: selectedNode.id, prevId: prevNode.id}, 
	                success:function(){
	                	refreshTree(parentNode.id);
	                }  
	         	});
			});
			
			$("#downBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				var parentNode = selectedNode.getParentNode();
				var nextNode = selectedNode.getNextNode();
				$.ajax({  
	                type: "POST",  
	                url: "dictionaryManage/down", 
	                async: true, 
	                cache: false, 
	                dataType: "json", 
	                data: { currentId: selectedNode.id, nextId: nextNode.id}, 
	                success:function() {
	                	alert("删除成功！");
	                	refreshTree(parentNode.id);
	                }  
	         	});
			});
			
			$("#addSubmitBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				var isParent = $("[name='type']:checked").val() == 0 ? true : false;
				var name = $("#addDiv [name='name']").attr("value");
				var value = $("#addDiv [name='value']").attr("value");
				$.ajax({  
	                type: "POST",  
	                url: "dictionaryManage/add", 
	                async: true, 
	                cache: false, 
	                dataType: "json", 
	                data: { parentId: selectedNode.id, isParent: isParent, name: name, value: value }, 
	                success:function() {
	                	alert("添加成功！");
	                	refreshTree(selectedNode.id);
	                }  
	         	});
			});
			
			$("#editSubmitBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				var name = $("#editDiv [name='name']").attr("value");
				var value = $("#editDiv [name='value']").attr("value");
				$.ajax({  
	                type: "POST",  
	                url: "dictionaryManage/update", 
	                async: true, 
	                cache: false, 
	                dataType: "json", 
	                data: { id: selectedNode.id, name: name, value: value }, 
	                success:function() {
	                	alert("修改成功！");
	                	refreshTree(selectedNode.id);
	                }  
	         	});
			});
			
			$("#addCancelBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				showInfoDiv(selectedNode);
			});
			
			$("#editCancelBtn").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var selectedNode = zTree.getSelectedNodes()[0];
				showInfoDiv(selectedNode);
			});
			
		});
    </script>
    <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
  
  <body>
	<div class="ceshi" style="position:relative;">
		<div id="depMenuDiv" style="width: 320px;position:absolute;height: 20px;background:#C9EDCC;padding:5px;font-size: 12px;FONT-FAMILY: "����", "Verdana", "Arial";">
			<a href="javascript:void(0)"  id="addBtn" plain="true" class="easyui-linkbutton" iconCls="icon-add" >添加</a>
	 		<a href="javascript:void(0)"  id="editBtn" plain="true" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a>
	 		<a href="javascript:void(0)"  id="deleteBtn" plain="true" class="easyui-linkbutton" iconCls="icon-cancel" >删除</a>
	 		<a href="javascript:void(0)"  id="upBtn" plain="true" class="easyui-linkbutton" iconCls="icon-up" >上调</a>
	 		<a href="javascript:void(0)"  id="downBtn" plain="true" class="easyui-linkbutton" iconCls="icon-down" >下调</a>
		</div>
		<div id="treeDiv" style="width: 320px;height: 500px;overflow: auto;position: absolute;top: 37px;">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div id="dataDiv" style="position:absolute;left: 400px;height: 700px;width: 400px; top: 30px;">
			<div id="infoDiv" style="height: 600px;">
				<table border="0" bgcolor="#cccccc" cellspacing="1" cellpadding="5" width="100%" class="toptablebg">
			    	<thead>
			    		<tr><td colspan="2" align="center" style="font-weight: bolder;">当前选中参数信息</td></tr>
			    	</thead>
			    	<tbody>
			    		<tr align="center">
			    			<td width="50%">名</td><td width="50%"><span name="name"></span></td>
			    		</tr>
			    		<tr align="center">
			    			<td width="50%">值</td><td width="50%"><span name="value"></span></td>
			    		</tr>
			    	</tbody>
			    </table>
			</div>
			<div id="addDiv" style="height: 600px;">
				<table border="0" bgcolor="#cccccc" cellspacing="1" cellpadding="5" width="100%" class="toptablebg">
			    	<thead>
			    		<tr><td colspan="2" align="center" style="font-weight: bolder;">参数信息</td></tr>
			    	</thead>
			    	<tbody>
			    		<tr align="center">
			    			<td width="50%">名</td><td width="50%"><input name="name" type="text" /></td>
			    		</tr>
			    		<tr align="center">
			    			<td width="50%">值</td><td width="50%"><input name="value" type="text" /></td>
			    		</tr>
			    		<tr align="center">
			    			<td width="50%">类别</td>
			    			<td width="50%">
			    				<input name="type" type="radio" value="0" checked="checked" /><span>分类</span>
			    				<input name="type" type="radio" value="1" /><span>参数</span>
			    			</td>
			    		</tr>
			    	</tbody>
			    	<tfoot>
			    		<tr>
			    			<td colspan="2" style="text-align:center">
			    				<input id="addSubmitBtn" type="button" value="确认" />
				          		<input id="addCancelBtn" type="button" value="取消" />
				            </td>
			    		</tr>
			    	</tfoot>
			    </table>
			</div>
			<div id="editDiv" style="height: 600px;">
				<table border="0" bgcolor="#cccccc" cellspacing="1" cellpadding="5" width="100%" class="toptablebg">
			    	<thead>
			    		<tr><td colspan="2" align="center" style="font-weight: bolder;">参数信息</td></tr>
			    	</thead>
			    	<tbody>
			    		<tr align="center">
			    			<td width="50%">名</td><td width="50%"><input name="name" type="text" /></td>
			    		</tr>
			    		<tr align="center">
			    			<td width="50%">值</td><td width="50%"><input name="value" type="text" /></td>
			    		</tr>
			    	</tbody>
			    	<tfoot>
			    		<tr>
			    			<td colspan="2" style="text-align:center">
			    				<input id="editSubmitBtn" type="button" value="确认" />
				          		<input id="editCancelBtn" type="button" value="取消" />
				            </td>
			    		</tr>
			    	</tfoot>
			    </table>
			</div>
		</div>
    </div>
  </body>
</html>
