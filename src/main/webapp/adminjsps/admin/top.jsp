<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    <base target="body">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {font-size: 10pt;}
	a {color: #aaa;}
</style>
  </head>
  
  <body style="background: rgb(78,78,78);color: #fff;">
<h1 style="text-align: center; line-height: 30px;">网上书城系统后台管理</h1>
<div style="line-height: 10px;">
	<c:choose>
		<c:when test="${empty sessionScope.admin.adminname }">
			<a target="_top" href="<c:url value='/adminjsps/login.jsp'/>">请登录</a>
	</span>
		</c:when>
		<c:otherwise>
			<span>管理员:${sessionScope.admin.adminname }</span>
			<a target="_top" href="<c:url value='/adminjsps/login.jsp'/>">退出</a>
			<span style="padding-left:50px;">
			<a href="<c:url value='/admin/findAllCategory.action'/>">分类管理</a>
			<a href="<c:url value='/adminjsps/admin/book/main.jsp'/>">图书管理</a>
			<a href="<c:url value='/admin/findAllOrder.action'/>">订单管理</a>
		</c:otherwise>
	</c:choose>
		
	
	
</div>
  </body>
</html>
