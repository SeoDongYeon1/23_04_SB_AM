<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Write"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	<form method="post" action="doWrite"></form>

<%@ include file="../common/foot.jspf" %>