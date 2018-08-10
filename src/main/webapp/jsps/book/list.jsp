<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
  </head>
  
  <body>

<ul>
<c:forEach items="${pb.beanList }" var="book">
  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/book/load.action?bid=${book.bid }'/>">
      <img src="<c:url value='/${book.imageB }'/>" border="0"/>
    </a>
    <p class="price">
		<span class="price_n">&yen;${book.currprice }</span>
		<span class="price_r">&yen;${book.price }</span>
		(<span class="price_s">${book.discount }折</span>)
	</p>
	<%--    需要实现按作者，按出版社查询  --%>
	<%--	因为存在中文 传到servlet需要进行转码 用到<c:url> --%>
	<%--	<c:url>标签会自动对参数进行url编码 --%>
	
	<c:url value="/book/findByAuthor.action" var="authorUrl">
		<%-- <c:param name="method" value="findByAuthor"></c:param> --%>
		<c:param name="author" value="${book.author }"></c:param>
	</c:url>
	<c:url value="/book/findByPress.action?press=${book.press }&pc=1" var="pressUrl">
		<%-- <c:param name="method" value="findByPress"></c:param> --%>
		<!-- 2018.7.29 使用c标签 会自动对参数进行编码，不知为什么会导致出现乱码 -->
		<%-- <c:param name="press" value="${book.press }"></c:param> --%>
	</c:url>
	
	<p><a id="bookname" title="${book.bname }" href="<c:url value='/book/load.action?bid=${book.bid }'/>">${book.bname }</a></p>
	<p><a href="${authorUrl }" name='P_zz' title='${book.author }'>${book.author }</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="${pressUrl }">${book.press }</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
  </div>
  </li>

</c:forEach>






</ul>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>

  </body>
 
</html>

