<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>
<%
	Article article = (Article) request.getAttribute("article");
	int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Write"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	

<%@ include file="../common/foot.jspf" %>