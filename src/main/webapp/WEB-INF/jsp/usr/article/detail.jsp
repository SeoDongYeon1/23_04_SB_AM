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
	params.memberId = parseInt('${loginedMemberId}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		
		if(localStorage.getItem(localStorageKey)) {
			return;
		}
		
		localStorage.setItem(localStorageKey, true);
		
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
	
	
	function ArticleDetail__doGoodPoint(articleId, memberId) {
    $.ajax({
        url: "/usr/reactionPoint/doGoodReaction",
        type: "POST",
        data: {
            id: articleId,
            memberId: memberId
        },
        dataType: "json",
        success: function(response) {
            // 응답 처리 코드
            if (response.resultCode == "S-1") {
                const likeCountElement = $("#likeCount_" + articleId);
                const newLikeCount = response.data1;
                likeCountElement.text(newLikeCount);
            }
        },
        error: function(xhr, status, error) {
            // 오류 처리 코드
        }
    });
}


	
</script>

	<hr />
	
	<div class="mt-8 text-xl">
		<table class="table-box-type-1 table table-zebra w-full">
			<tr>
				<th>게시판</th>
				<th>${article.board_name }</th>
			</tr>
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

			<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }" class="btn btn-outline" type="button">
	  			<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="red" viewBox="0 0 24 24" stroke="currentColor">
	  				<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
	  			</svg>
				<span id="likeCount_${article.id}">${article.goodReactionPoint}</span>
			</a>
			
			<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }" class="btn btn-outline" type="button">
			  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18,4h3v10h-3V4z M5.23,14h4.23l-1.52,4.94C7.62,19.97,8.46,21,9.62,21c0.58,0,1.14-0.24,1.52-0.65L17,14V4H6.57 C5.5,4,4.59,4.67,4.38,5.61l-1.34,6C2.77,12.85,3.82,14,5.23,14z" />
			  </svg>
			  <span id="DisLikeCount_${article.id}">${article.badReactionPoint}</span>
			</a>

		<!-- ver1 -->
		<c:if test="${article.actorCanDelete }">
			<a class= "btn btn-outline" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;" href="doDelete?id=${article.id }">삭제</a>
		</c:if>
		
		<!-- ver2 -->
		<%if(loginedMemberId==article.getMemberId()) {%>
			<a class= "btn btn-outline" href="modify?id=${article.id }">수정</a>
		<%}%>
		
		<div>
		<button style="margin-top: 20px;" class= "btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
		</div>
	</div>
	
	<!-- 커스텀 -->
	<style type="text/css">
	.table-box-type-1 {
		margin-left: auto;
		margin-right: auto;
		width: 500px;
	}
	
	.btns {
		text-align: center;
	}
	</style>
<%@ include file="../common/foot.jspf" %>