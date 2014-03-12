<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>平台登陆</title>
	<script type="text/javascript" src="static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="static/js/md5.js"></script>
	<style type="text/css">
	<!--
	html,body {
		margin: 0px;
		padding: 0px;
		height: 100%;
		width: 100%;
	}
	-->
	</style>
	 
	<script type="text/javascript">
		function init(){
			var str=document.getElementById("form1:textfield");		
			str.focus();
		}
		$(function(){
			$("#submitBtn").click(function(){
				alert(document.getElementById("form1:password").value);
				var hash=hex_md5(document.getElementById("form1:password").value);  
				document.getElementById("form1:password").value=hash;
				alert(document.getElementById("form1:password").value);
				$("#form1").submit();
			});
		})
	</script>
</head>
<body onLoad="init()" bgcolor="#9bd6d4">
			<table width="100%" border="0" height="100%" cellspacing="0"
				cellpadding="0" style="background: url('static/images/loginbg.jpg') top center no-repeat;">
				<tr>
					<td height="538" align="left" valign="top">
						<div style="height: 350px; width: 100%;">



						</div>
						<div style="height: 188px;; width: 100%;">
							<table width="100%" height="188px;" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td width="50%">
										&nbsp;
									</td>
									<td width="50%" align="left" valign="top"
										style="padding-left: 150px;">
										
	<form id="form1" name="form1" method="post" action="${basePath }login.do">

											<table width="200" border="0" cellspacing="0" cellpadding="0"
												style="font-size: 15px; font-weight: bold;">
												<tr>
													<td width="80" height="40" align="right" valign="middle">
														用户名&nbsp;
													</td>
													<td height="40" align="left" valign="middle">
														<input id="form1:textfield" type="text" name="userName" style="width:100px;" tabindex="1" />
													</td>
												</tr>
												<tr>
													<td width="80" height="40" align="right" valign="middle">
														密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;
													</td>
													<td height="40" align="left" valign="middle">
														<input id="form1:password" type="password" name="userPassword" value="" style="width: 100px;" tabindex="2" />
													</td>													
												</tr>
												<tr>	
													<td height="40" colspan="2" align="center" valign="middle" style="padding-left:10px;">
														<input type="submit"  id="submitBtn" value="&#25552; &#20132;" style="background:none; border:none; width:60px; height:23px; background:url('static/images/butn.jpg'); color:#ffffff; cursor:pointer;" tabindex="3" />
														&nbsp;
														<input type="reset" id="resetBtn" value="&#37325; &#32622;" style="background:none; border:none; width:60px; height:23px; background:url('static/images/butn.jpg'); color:#ffffff; cursor:pointer;" tabindex="4" />
													</td>
												</tr>
											</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		<!-- MYFACES JAVASCRIPT -->

</body>

</html>