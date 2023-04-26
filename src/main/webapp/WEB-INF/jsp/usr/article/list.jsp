<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
int totalPage = (int) request.getAttribute("totalPage");
int cur_Page = (int) request.getAttribute("page");


int displayPage = 10;
int startPage = ((cur_Page-1)/displayPage)*displayPage+1;
int endPage = startPage+displayPage-1;
%>
<c:set var="pageTitle" value="${board.name }"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	<div class="mt-8 text-xl mx-auto px-3">
		<div style="text-align:center;">전체 게시물 갯수 : ${articlesCount }개</div>
		<table class="table-box-type-1 table table-zebra w-full" style="text-align:center;">
		
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성날짜</th>
				<th>작성자</th>
				<th>조회수</th>
			</tr>
		<c:forEach var="article" items="${articles }">
			<tr>
				<th><div class="badge badge-outline">${article.id }</div></th>
				<th><a href="detail?id=${article.id }">${article.title }</a></th>
				<th>${article.regDate.substring(0,10) }</th>
				<th>${article.extra__writer }</th>
				<th>${article.hitCount }</th>
			</tr>

		</c:forEach>
		</table>
	</div>

	<div class="pagenation" style="text-align: center; margin-top:20px;">
		<c:set var="baseUri" value="?boardId=${boardId }" />
		<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode }" />
		<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword }" />
		<%
		if(cur_Page > 10) {
			%>
			<a class = "btn btn-outline first_page" href="${baseUri }&page=1">◀◀</a>	
			<%
		}
		if(endPage > totalPage)	{
			endPage = totalPage;
		}
							
	    if(startPage > displayPage) { 
		%>
			<a class="btn btn-outline" href="${baseUri }&page=<%=startPage - 10%>">이전</a>
		<%
		}
	    
		for(int i=startPage; i <= endPage; i++){%>
				<a class= "btn btn-outline <%=cur_Page == i ? "btn-active" : "" %>" href="${baseUri }&page=<%=i%>"><%=i %></a>
		<%}
		
		if(endPage < totalPage)	{
		%>
			<a class="btn btn-outline" href="${baseUri }&page=<%=startPage + 10 %>">다음</a>
		<%
		}
		if(cur_Page < totalPage) {
			%>
			<a class = "last_page btn btn-outline" href="${baseUri }&page=<%=totalPage%>">▶▶</a>	
			<%
		}
		%>
	</div>
	
	<form method="post" action="list">
		<input type="hidden" name="boardId" value="${param.boardId }" />
		<div style="text-align: center; margin-top: 20px;">
			<div style="display: inline-block; ">
			<select data-value="${searchKeywordTypeCode }" style="border-color: black;" name=searchKeywordTypeCode class="select select-ghost" >
				<option disabled selected>검색 설정</option>
				<option value="title">제목만</option>
				<option value="body">내용만</option>
				<option value="title,body">제목+내용</option>
			</select>
			</div>
		<div style="display: inline-block; ">	
				<div style="font-size: 17px; font-weight: bold; ">
					<input class="input input-bordered" type="text" name="searchKeyword" value="${param.searchKeyword }" placeholder="검색어를 입력해주세요." maxlength="20" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 300px;"/>
				</div>
		</div>		
		<div style="border-radius: 8px; display: inline-block;">
				<button class="btn btn-outline" style="padding: 0 20px; " type="submit">검색</button>
		</div>
		
		</div>
	</form>
	
	<style type="text/css">	
	body {
	  height: 1000px;
	}
	a:hover {
		text-decoration: underline;
	}
	
	.table-box-type-1 {
		margin-left: auto;
		margin-right: auto;
		width: 700px;
	}
	</style>
<%@ include file="../common/foot.jspf" %>