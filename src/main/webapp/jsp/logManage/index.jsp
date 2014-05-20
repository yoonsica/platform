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
    
    <title>系统模块管理</title>
    
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
	
	input { width: 128px; }
	
	label { margin-left: 8px; }
	
    .base-div { 
    	width: 1000px; 
    	height: 600px; 
    	left: 50%; 
    	margin: 0 0 0 -500px; 
    	position: absolute; 
    }
	</style>
  	<script type="text/javascript">
  	
		function logFilter(data){
		    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array
		        data = {
		            total: data.length,
		            rows: data
		        }
		    }
		    
		    if (!data.originalRows){
		        data.originalRows = (data.rows);
		    }
		    
		    var dg = $(this);
   		    var opts = dg.datagrid('options');
   		    var pager = dg.datagrid('getPager');
   		    pager.pagination({
   		    	//showRefresh: false, 
   		        onSelectPage:function(pageNum, pageSize) {
   		            opts.pageNumber = pageNum;
   		            opts.pageSize = pageSize;
   		            dg.datagrid('loadData', data)
   		        }
   		    });
   		    
		    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
		    var end = start + parseInt(opts.pageSize);
		    data.rows = (data.originalRows.slice(start, end));
		    return data;
		}
    
    	function formatNumber(n) {
    		return n < 10 ? ('0' + n) : n;
    	}
    	
    	function dateFormatter(date) {
    		// 将毫秒转成日期
    		var date = new Date(date);
    		var y = date.getFullYear();  
	        var M = date.getMonth() + 1;  
	        var d = date.getDate();  
	        var h = date.getHours();
	        var m = date.getMinutes();
	        var s = date.getSeconds();
	        return y + '-' + formatNumber(M) + '-' + formatNumber(d) + ' ' + formatNumber(h) + ':' + formatNumber(m) + ':' + formatNumber(s);
    	}
    	
    	function dateParser(str) {
    		str = str.replace(/-/g,"/");
    		return new Date(str);
    	}
    	
    	function typeFormatter(type) {
    		return type.name;
    	}
    	
    	function personFormatter(person) {
    		return person.name;
    	}
    	
    	function idSorter(a, b) {
    		return a > b;
    	}
	    
		$(document).ready(function(){
			$.ajax({  
                type: "POST",  
                url: "logManage/getLogTypes",
                async: true,  
                success:function(logTypes) {
                	$("[name='logType']").combobox({
                		data: logTypes, 
        			    valueField: 'id', 
        			    textField: 'name', 
        			    editable: false, 
        			    required: true, 
        			    width: 112, 
        			    panelHeight: 86, 
        			    value: 0
        			});
                }  
         	});
		    
		    $("[name='beginTime']").datetimebox({
		    	width: 150, 
		        editable: false, 
		        value: '2014-05-01 08:00:00', 
		        formatter: dateFormatter, 
				parser: dateParser
		    });
		    
		    $("[name='endTime']").datetimebox({
		    	width: 150, 
		        editable: false, 
		        value: dateFormatter(new Date()), 
		        formatter: dateFormatter, 
				parser: dateParser
		    });
		    
		    var queryParams = { 
	    		typeId: 0, 
	    		content: "", 
	    		personName: "", 
	    		ip: "", 
	    		beginTime: dateParser($('#beginTime').datetimebox('getValue')), 
	    		endTime: dateParser($('#endTime').datetimebox('getValue')), 
	    		pageIndex: 1, 
	    		pageSize: $('#log').datagrid('options').pageSize, 
	    		sortNames: $('#log').datagrid('options').sortName, 
	    		sortOrders: $('#log').datagrid('options').sortOrder
		    };
		    
		    $('#log').datagrid({
		    	url: "logManage/show", 
		    	queryParams: queryParams, 
				onSortColumn: function(sortNames, sortOrders) {
					queryParams["sortNames"] = sortNames;
					queryParams["sortOrders"] = sortOrders;
					
					$('#log').datagrid('load', queryParams);
					$('#log').datagrid('getPager').pagination('select', queryParams["pageIndex"]);
				}
		    });
		    $('#log').datagrid('getPager').pagination({
   		        onSelectPage: function(pageIndex, pageSize) {
   		        	queryParams["pageIndex"] = pageIndex;
   		        	queryParams["pageSize"] = pageSize;
   		        
   		        	$.ajax({  
   		                type: "POST",  
   		                url: "logManage/show",
   		                dataType: "json", 
   		                data: queryParams, 
   		                async: false,  
   		                success:function(data) {
   		                	$('#log').datagrid('loadData', data);
   		                }  
   		         	});
   		        	
   		        }
   		    });
		    
		    
		    $("#inquiryBtn").click(function() {
			    queryParams = { 
		    		typeId: $("#logType").combobox('getValue'), 
		    		content: $("[name='logContent']").val(), 
		    		personName: $("[name='personName']").val(), 
		    		ip: $("[name='ip']").val(), 
		    		beginTime: dateParser($('#beginTime').datetimebox('getValue')), 
		    		endTime: dateParser($('#endTime').datetimebox('getValue')), 
		    		pageIndex: 1, 
		    		pageSize: $('#log').datagrid('options').pageSize 
			    };
			    
			    $('#log').datagrid('load', queryParams);
		    });
		    
		    $("#deleteBtn").click(function() {
		    	var logs = $('#log').datagrid('getSelections');
		    	
		    	if(logs.length == 0) {
		    		alert("请先选中要删除的行！");
		    		return;
		    	}
		    	
		    	if(!confirm("你确认要删除吗？")) {
		    		$('#log').datagrid('clearSelections')
		    		return;
		    	}
		    	
				var ids = new Array();
				for(var i=0; i<logs.length; i++){
				    ids.push(logs[i].id);
				}
				
				$.ajax({  
	                type: "POST",  
	                url: "logManage/delete",
	                async: false,  
	                cache: false, 
	                traditional: true, 
	                dataType: "json", 
	                data: { ids: ids }, 
	                success: function() {
	                	$('#log').datagrid('load', queryParams);
	                	// 延迟设置太小还是会同步执行
	                	setTimeout(function() { alert("删除成功！") }, 50);
	                }  
	         	});
		    });
		    
		});
    </script>
  </head>
  
  <body>
	<div class="base-div">
		<form style="margin: 10px 0;">
			<fieldset>
				<legend>查询信息</legend>
				<div>
					<label for="logContent">日志内容</label>
					<input type="text" name="logContent" />
					<label for="personName">用户名称</label>
					<input type="text" name="personName" />
					<label for="ip">用户IP</label>
					<input type="text" name="ip" />
				</div>
				<div>
					<label for="beginTime">时间范围</label>
					<input id="beginTime" name="beginTime" />
        			<span>----></span>
					<input id="endTime" name="endTime" />
        			<label for="logType">日志类型</label>
					<input id="logType" name="logType" />
					<a id="inquiryBtn" href="javascript:void(0)" class="easyui-linkbutton" 
						data-options="iconCls: 'icon-search'">查询</a>
					<a id="deleteBtn" href="javascript:void(0)" class="easyui-linkbutton" 
						data-options="iconCls: 'icon-remove'">删除</a>
				</div>
			</fieldset>
		</form>
		<table id="log" class="easyui-datagrid" title="日志列表" 
			style="width: 1000px; height: 380px;" 
			pagination="true" pageSize="10" singleSelect="false" 
			multiSort="true" remoteSort="false" sortName="id" sortOrder="asc">
	        <thead>
	            <tr>
	            	<th field='ck' checkbox="true"></th>
	                <th field="id" width="50" sortable="true">ID</th>
	                <th field="content" width="400" sortable="true">日志内容</th>
	                <th field="type" width="100" sortable="true" formatter="typeFormatter">类型</th>
	                <th field="person" width="100" sortable="true" formatter="personFormatter">用户</th>
	                <th field="ip" width="150" sortable="true">IP</th>
	                <th field="recordTime" width="164" sortable="true" formatter="dateFormatter">记录时间</th>
	            </tr>
	        </thead>
	    </table>
    </div>
  </body>
</html>
