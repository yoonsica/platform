<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>站内搜索</title>
    
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
	}
	
	input { width: 256px; }
	
	form { margin: 10px 0; }
	
	label { margin-left: 8px; }
	
	.align-center { text-align: center; }
	
    .base-div { 
    	width: 800px; 
    	height: 600px; 
    	left: 50%; 
    	margin: 0 0 0 -400px; 
    	position: absolute; 
    }
	</style>
  	<script type="text/javascript">
    	
  		var searchTypes = [ "全部", "部门", "角色", "资源", "人员" ];
  		var urls = [ "", "showDepartmentInfo", "showRoleInfo", "showResourceInfo", "showPersonInfo" ];
  		
    	function typeFormatter(typeId) {
    		return searchTypes[typeId];
    	}
    	
    	function reloadSearchTable(searchTypeId, searchText, pageIndex, pageSize) {
    		$.ajax({  
                type: "POST",  
                url: "SiteSearch/showSearchResults",
                async: true, 
                dataType: "json", 
                data: { searchTypeId: searchTypeId, searchText: searchText, pageIndex: pageIndex, pageSize: pageSize }, 
                success: function(searchResults) {
                	$('#searchResult').datagrid('loadData', searchResults)
                }  
         	});
    	}
    	
		$(document).ready(function(){
		    $('#searchResult').datagrid({
		    	onClickRow: function(rowIndex, rowData) {
		    		window.location.href = "${basePath}SiteSearch/" + urls[rowData.typeId] + "?id=" + rowData.id;
			    }
		    });
		    
		    $('#searchResult').datagrid('getPager').pagination({
   		        onSelectPage: function(pageIndex, pageSize) {
   		        	var searchTypeId = $("#searchType").val();
   			    	var searchText = $("#searchText").val();
   			    	if(searchText == "") {
   			    		alert("请输入要查找的内容！");
   			    		return;
   			    	}
   		        	reloadSearchTable(searchTypeId, searchText, pageIndex, pageSize);
   		        }
   		    });
		    
		    
		    $("#searchBtn").click(function() {
		    	var searchTypeId = $("#searchType").val();
		    	var searchText = $("#searchText").val();
		    	if(searchText == "") {
		    		alert("请输入要查找的内容！");
		    		return;
		    	}
		    	reloadSearchTable(searchTypeId, searchText, 1, 10);
		    });
		    
		});
    </script>
  </head>
  
  <body>
	<div class="base-div">
		<form>
			<fieldset class="align-center">
				<select id="searchType" name="searchType">
					<!-- <option value="0">全部</option> -->
					<option value="1">部门</option>
					<option value="2">角色</option>
					<option value="3">资源</option>
					<option value="4">人员</option>
				</select>
				<input id="searchText" />
				<a id="searchBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls: 'icon-search'">搜索</a>
			</fieldset>
		</form>
		<table id="searchResult" class="easyui-datagrid" title="日志列表" 
			style="width: 800px; height: 380px;" 
			pagination="true" pageSize="10" singleSelect="true">
	        <thead>
	            <tr>
	                <th field="id" width="100">ID</th>
	                <th field="result" width="600">结果</th>
	                <th field="typeId" width="95" formatter="typeFormatter">类型</th>
	            </tr>
	        </thead>
	    </table>
    </div>
  </body>
</html>
