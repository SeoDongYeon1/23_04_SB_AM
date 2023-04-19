<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article List"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	<div class="mt-8 text-xl">
		<table class="table-box-type-1">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성날짜</th>
				<th>작성자</th>
			</tr>
			
		<c:forEach var="article" items="${articles }">
			<tr>
				<th>${article.id }</th>
				<th><a href="detail?id=${article.id }">${article.title }</a></th>
				<th>${article.regDate.substring(0,10) }</th>
				<th>${article.memberId }</th>
			</tr>

		</c:forEach>
		</table>
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