<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<link href="<c:url value='/jsps/css/user/regist.css'/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
 	<script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'/>"></script>
 
 </head>
  <body>
    <div id="divMain">
        <div id="divTitle"><span id="spanText">新用户注册</span></div>
        <div id="divTable">
        <form action="<c:url value='/user/regist.action'/>" method="post" id="registForm">
       <!--  <input type="hidden" name="method" value="regist"> -->
        	<table id="table">
        		<tr>
        		 	<td class="tdText">用户名：</td>
        		 	<td class="tdInput">
        		 		<input type="text" class="inputText" name="loginname" id="loginname" value="${form.loginname}">
        		 	</td>
        		 	<td class="tdError"><label class="Error" id="loginnameError">${errors.loginname}</label></td>
        		</tr>
        		<tr>
        		 	<td class="tdText">登录密码：</td>
        		 	<td class="tdInput"><input type="password" class="inputText" name="loginpass" id="loginpass" value="${form.loginpass }"></td>
        		 	<td class="tdError"><label class="Error" id="loginpassError">${errors.loginpass }</label></td>
        		</tr>
        		<tr>
        		 	<td class="tdText">确认密码：</td>
        		 	<td class="tdInput"><input type="password" class="inputText" name="reloginpass" id="reloginpass" value="${form.reloginpass }"></td>
        		 	<td class="tdError"><label class="Error" id="reloginpassError">${errors.reloginpass }</label></td>
        		</tr>
        		<tr>
        		 	<td class="tdText">Email：</td>
        		 	<td class="tdInput"><input type="text" class="inputText" name="email" id="email" value="${form.email }"></td>
        		 	<td class="tdError"><label class="Error" id="emailError">${errors.email }</label></td>
        		</tr>
        	<%-- 	<tr>
        		 	<td class="tdText">图像验证码：</td>
        		 	<td class="tdInput"><input type="text" class="inputText" name="verifycode" id="verifycode" value="${form.verifycode }"></td>
        		 	<td class="tdError"><label class="Error" id="verifycodeError">${errors.verifycode }</label></td>
        		</tr> --%>
        		<%-- <tr>
        		 	<td></td>
        		 	<td><div id="imgDiv" ><img id="img" src="<c:url value='/VerifyCodeServlet'/>"></div></td>
        		 	<td><a href="javascript:_hyz()" >换一张</a></td>
        		</tr> --%>
        		<tr>
        		 	<td></td>
        		 	<td><input id="submitBtn" type="image" src="<c:url value='/images/regist1.jpg'/>"></td>
        		 	<td></td>
        		</tr>
        	</table>
        	</form>
        </div>
    </div>
</body>
</html>
	