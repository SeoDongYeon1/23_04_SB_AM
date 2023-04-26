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
		<div style="text-align:center;">${articlesCount }개</div>
		<table class="table-box-type-1 table table-zebra w-full" style="text-align:center;">
		
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성날짜</th>
				<th>작성자</th>
			</tr>
		<c:forEach var="article" items="${articles }">
			<tr>
				<th><div class="badge badge-outline">${article.id }</div></th>
				<th><a href="detail?id=${article.id }">${article.title }</a></th>
				<th>${article.regDate.substring(0,10) }</th>
				<th>${article.extra__writer }</th>
			</tr>

		</c:forEach>
		</table>
	</div>

	<div class="pagenation" style="text-align: center; margin-top:20px;">
		<c:set var="baseUri" value="?boardId=${boardId }" />
		<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode}" />
		<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword}" />
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
	
	<form method="post" action="list" onsubmit = "return Search(this); return false;">
		<div style="text-align: center; margin-top: 20px;">
			<div style="display: inline-block; ">
			<select style="border-color: black;" id="Search" class="select select-ghost">
				<option disabled selected>검색 설정</option>
				<option class="title" value="1">제목만</option>
				<option class="body" value="2">내용만</option>
			</select>
			</div>
		<div style="display: inline-block; ">	
		<input type="hidden" name="search_option"/>
				<div style="font-size: 17px; font-weight: bold; ">
					<input class="search input input-bordered" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 300px;" type="text" value="${article.title }" name="searchKeyword"/>
				</div>
		</div>		
		<div style="border-radius: 8px; display: inline-block;">
				<button class="btn btn-outline" style="padding: 0 20px; " type="submit">검색</button>
		</div>
		
		</div>
	</form>
	
	<script>
	$(document).ready(function() {
	    $('#Search').on('change', function() {
	        $('input[name="search_option"]').val($(this).val());
	    });
	});

	function Search(form) {
	    var title = form.title.value.trim();		
	    var body = form.body.value.trim();		
	    
	    if(title.length == 0) {
	        alert('제목을 입력해주세요.');
	        form.title.focus(); 
	        return false;
	    }
	    if(body.length == 0) {
	        alert('내용을 입력해주세요.');
	        form.title.focus(); 
	        return false;
	    }
	    
	    return true;
	}
	</script>
	
	
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