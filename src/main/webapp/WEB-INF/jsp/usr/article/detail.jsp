<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>
<%
	Article article = (Article) request.getAttribute("article");
	int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Detail"/>
<%@ include file="../common/head.jspf" %>
<script>
	const params = {}
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		// 실전코드
		 	ArticleDetail__increaseHitCount();
		// 연습코드
		//setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
</script>

	<hr />
	
	<div class="mt-8 text-xl">
		<table class="table-box-type-1 table table-zebra w-full">
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
				<th>${article.regDate }</th>
			</tr>
			
			<tr>
				<th>수정날짜</th>
				<th>${article.updateDate }</th>
			</tr>
			
			<tr>
				<th>작성자</th>
				<th>${article.extra__writer }</th>
			</tr>
			<tr>
				<th>조회수</th>
				<th><span class="article-detail__hit-count">${article.hitCount }</span></th>
			</tr>
		</table>
	</div>
	<br />
	<div class="btns">
		<button class= "btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
		
		<!-- ver1 -->
		<c:if test="${article.actorCanDelete }">
			<a class= "btn btn-outline" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;" href="doDelete?id=${article.id }">삭제</a>
		</c:if>
		
		<!-- ver2 -->
		<%if(loginedMemberId==article.getMemberId()) {%>
			<xred><a class= "" href="modify?id=${article.id }">수정</a></xred>
		<%}%>
	</div>
	
	<!-- 커스텀 -->
	<style type="text/css">
	.table-box-type-1 {
		margin-left: auto;
		margin-right: auto;
		width: 500px;
	}
	xred{color:red;border:1px solid black;border-radius:5px;background-color:yellow;padding:11px;}
	xred:hover{background-color:gray;cusor:pointer;text-decoration:underline;color:blue;text-shadow:1px 1px 1px #ffffff;}
	
	.btns {
		text-align: center;
	}
	</style>
<%@ include file="../common/foot.jspf" %>