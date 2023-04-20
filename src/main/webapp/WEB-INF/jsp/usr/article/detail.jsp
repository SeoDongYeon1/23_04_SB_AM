<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Detail"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	<div class="mt-8 text-xl">
		<table class="table-box-type-1">
			<tr>
				<th>번호</th>
				<th>${article.id }</th>
			</tr>
			
			<tr>
				<th>제목</th>
				<th>${article.title }</th>
			</tr>
			
			<tr>
				<th>내용</th>
				<th>${article.body }</th>
			</tr>
			
			<tr>
				<th>작성날짜</th>
				<th>${article.regDate.substring(0,10) }</th>
			</tr>
			
			<tr>
				<th>수정날짜</th>
				<th>${article.updateDate.substring(0,10) }</th>
			</tr>
			
			<tr>
				<th>작성자</th>
				<th>${article.extra__writer }</th>
			</tr>
		</table>
		<a href="delete?id=${article.id }">삭제</a>
		<a href="modify?id=${article.id }">수정</a>
	</div>
	
	<div class="btns">
		<button type="button" onclick="history.back()">뒤로가기</button>
	</div>
	
	<style type="text/css">
	.table-box-type-1 {
		margin-left: auto;
		margin-right: auto;
		width: 500px;
	}
	
	a:hover {
		text-decoration: underline;
	}
	
	.btns {
		text-align: center;
	}
	</style>

<%@ include file="../common/foot.jspf" %>