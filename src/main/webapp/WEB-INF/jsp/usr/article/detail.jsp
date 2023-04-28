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
        url: "/usr/reaction/doGoodPoint",
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
			<tr>
				<th>싫어요</th>
				<th>${article.extra__badReactionPoint }</th>
			</tr>
			<tr>
				<th>총합</th>
				<th>${article.extra__sumReactionPoint }</th>
			</tr>
		</table>
	</div>
	<br />
	<div class="btns">
		<c:if test="${actorCanMakeReaction }">
			<a href="#" class="btn btn-outline" type="button" onclick="ArticleDetail__doGoodPoint(${article.id}, ${loginedMemberId})">👍 <span id="likeCount_${article.id}">${article.extra__goodReactionPoint}</span></a>
			<button class= "btn btn-outline" type="button" onclick="like_point()">👎</button>
		</c:if>
		
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