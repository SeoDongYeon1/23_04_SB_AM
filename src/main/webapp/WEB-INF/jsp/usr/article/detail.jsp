<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Detail"/>
<%@ include file="../common/head.jspf" %>
		<hr />
	
	<div>
		<div>번호: ${article.id }</div>
		<div>제목: ${article.title }</div>
		<div>내용: ${article.body }</div>
		<div>작성날짜: ${article.regDate }</div>
		<div>수정날짜: ${article.updateDate }</div>
		<div>작성자: ${article.memberId }</div>
	</div>
	
	<style type="text/css">
	a {
		text-decoration: none;
		font-size: 17px;
		font-weight: bold;
	}
	
	a:hover {
		color: #9a9ba1;
	}
	</style>

<%@ include file="../common/foot.jspf" %>