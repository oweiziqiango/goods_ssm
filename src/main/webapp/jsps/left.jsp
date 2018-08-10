<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">
/*
Q6MenuBar(第一个参数,第二个参数),
第一个参数要与对象名一致，第二个参数指的分类上的标题名
*/
var bar = new Q6MenuBar("bar", "网上书城");
$(function() {
	bar.colorStyle = 4;//指定的分类样式
	bar.config.imgDir = "<c:url value='/menu/img/'/>";//显示用的图标
	bar.config.radioButton=true;//
<c:forEach items="${parents}" var="parent">
	<c:forEach items="${parent.children}" var="children">
		//2018.7.27 尝试用一下restful接口格式
		//bar.add("${parent.cname}", "${children.cname}", "/goods_ssm/book/findByCategory.action?cid=${children.cid}&pc=1", "body");
		//2018.7.29 使用tomcat7:run 之后 访问localhost：8080后面不用带项目名称
		bar.add("${parent.cname}", "${children.cname}", "/book/findByCategory.action?cid=${children.cid}&pc=1", "body");
	</c:forEach>
</c:forEach>	
	
	$("#menu").html(bar.toString());
});
</script>
</head>
  
<body>  
  <div id="menu"></div>
</body>
</html>
