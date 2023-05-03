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


<!-- 메서드 생성 -->
 <script>
	
	
</script>

<!-- 리액션 실행 코드 -->
<script>
		
</script>		
<script>	
	   
</script>
 
 
<!-- 변수 생성 -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');

	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
	var isAlreadyAddBadRp = ${isAlreadyAddBadRp};
</script>

<!-- 메서드 생성 -->
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

	function checkAddRpBefore() {
    <!-- 변수값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
		if (isAlreadyAddGoodRp == true) {
			$('#likeButton').removeClass('btn-outline').addClass('btn-danger');
		} else if (isAlreadyAddBadRp == true) {
			$('#DislikeButton').removeClass('btn-outline').addClass('btn-danger');
		} else {
			return;
		}
		$(function() {
			checkAddRpBefore();
		});
	};
	
	
</script>	
<!-- 리액션 실행 코드 -->
<script>
        <!-- jsp 실행 이전의 리액션 여부 체크 및 버튼 색상 표현 -->
		$(function() {
			checkAddRpBefore();
		});
		
		 function doGoodReaction(articleId) {
		        $.ajax({
		            url: '/usr/reactionPoint/doGoodReaction',
		            type: 'POST',
		            data: {relTypeCode: 'article', relId: articleId},
		            dataType: 'json',
		            success: function(data) {
		                if (data.resultCode.startsWith('S-')) {
		                    var likeButton = $('#likeButton');
		                    var likeCount = $('#likeCount');
		
		                    if (data.resultCode == 'S-1') {
		                        likeButton.removeClass('btn-danger').addClass('btn-outline');
		                        likeCount.text(parseInt(likeCount.text()) - 1);
		                    } else {
		                        likeButton.removeClass('btn-outline').addClass('btn-danger');
		                        likeCount.text(parseInt(likeCount.text()) + 1);
		                    }
		                } else {
		                    alert(data.msg);
		                }
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		                alert('오류가 발생했습니다: ' + textStatus);
		            }
		        });
		    }
			
		function doBadReaction(articleId) {
		      $.ajax({
		          url: '/usr/reactionPoint/doBadReaction',
		          type: 'POST',
		          data: {relTypeCode: 'article', relId: articleId},
		          dataType: 'json',
		          success: function(data) {
		              if (data.resultCode.startsWith('S-')) {
		                  var DislikeButton = $('#DislikeButton');
		                  var DislikeCount = $('#DislikeCount');
		                  
		                  if (data.resultCode == 'S-1') {
		                  	DislikeButton.removeClass('btn-danger').addClass('btn-outline');
		                    DislikeCount.text(parseInt(DislikeCount.text()) - 1);
		                  } else {
		                  	DislikeButton.removeClass('btn-outline').addClass('btn-danger');
		                    DislikeCount.text(parseInt(DislikeCount.text()) + 1);
		                  }
		              } else {
		                  alert(data.msg);
		              }
		          },
		          error: function(jqXHR, textStatus, errorThrown) {
		              alert('오류가 발생했습니다: ' + textStatus);
		          }
		      });
		  }
		
		$(function() {
			checkAddRpBefore();
		});
</script>   

	<hr />
	
	<div class="mt-8 text-xl">
		<table class="table-box-type-1 table table-zebra" style="width: 700px;">
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
		
			<button id="likeButton" class="btn btn-outline" type="button" onclick="doGoodReaction(${param.id})">
			  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
			  </svg>
			  <span id="likeCount">${article.goodReactionPoint}</span>
			  
			</button>
			<button id="DislikeButton" class="btn btn-outline" type="button" onclick="doBadReaction(${param.id})">
			  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18,4h3v10h-3V4z M5.23,14h4.23l-1.52,4.94C7.62,19.97,8.46,21,9.62,21c0.58,0,1.14-0.24,1.52-0.65L17,14V4H6.57 C5.5,4,4.59,4.67,4.38,5.61l-1.34,6C2.77,12.85,3.82,14,5.23,14z" />
			  </svg>
			  <span id="DislikeCount">${article.badReactionPoint}</span>
			</button>
		
		<br />
		<br />
		<!-- ver1 -->
		<c:if test="${article.actorCanDelete }">
			<a class= "btn btn-outline" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;" href="doDelete?id=${article.id }">삭제</a>
		</c:if>
		
		<!-- ver2 -->
		<%if(loginedMemberId==article.getMemberId()) {%>
			<a class= "btn btn-outline" href="modify?id=${article.id }">수정</a>
		<%}%>
		<button class= "btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
	</div>
	
	<div class="reply_text">
		댓글
		<div class="mt-8 text-xl mx-auto px-3 reply_box">
			<c:forEach var="reply" items="${replies }"> 
				<div class="reply_top flex justify-between">
					<div class="reply_writer">${reply.extra__writer }</div> <div class="reply_regDate">${reply.regDate }</div>
				</div>
				<br />
				<div class="reply_body">
					${reply.body }
					<hr />
					<br />
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:if test="${rq.logined }">
		<div style="text-align:center; margin-top: 30px;">
			<div style="font-weight:bold; font-size: 17px;">
				댓글
			</div>
			<form method= "post" action="../reply/doWrite" onsubmit = "return ReplyWrite__SubmitForm(this); return false;" style="width: 700px; height: 250px; border:2px solid black; display: inline-block;  border-radius: 8px;" >
				<br />
				<input type="hidden" name="relTypeCode" value="article" />
				<input type="hidden" name="relId" value="${article.id }" />
				<div style="display: inline-block ; text-align:left;">
					<div style="font-size: 17px; font-weight: bold;">
						내용
						<br />
						<textarea class="body textarea textarea-bordered" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 650px; height: 80px;" name="body"></textarea>
					</div>
					<br />
				</div>
				<br />
				<div style="border-radius: 8px; display: inline-block; width: 200px;">
					<button class="btn btn-outline" style="padding: 0 20px;" type="submit">댓글 작성</button>
				</div>
			</form>
		</div>
	</c:if>
<script>
	let ReplyWrite__SubmitFormDone = false;
	
	function ReplyWrite__SubmitForm(form) {
		if(ReplyWrite__SubmitFormDone) {
			return;
		}
		
	    var body = form.body.value.trim();
	    
	    if(body.length < 2) {
	        alert('내용을 2글자 이상 입력해주세요.');
	        form.body.focus();
	        return false;
	    }
	    
	    ReplyWrite__SubmitFormDone = true;
	    form.submit();
	}
</script>	

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
	
	.btns > .btn {
		width: 100px;
		height: 40px;
	}
	
	.reply_box {
		border: 2px solid black;
		text-align: left;
		font-weight: bold;
		width: 700px;
	}
	
	.reply_text {
		font-size: 25px;
		margin: 20px 0;
		text-align: center;
	}
	
	.reply_writer .reply_body{
		font-size: 20px;
	}
	
	.reply_regDate {
		font-size: 15px;
	}
	
	</style>
<%@ include file="../common/foot.jspf" %>