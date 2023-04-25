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
		<%
		if(cur_Page > 10) {
			%>
			<a class = "btn btn-outline first_page" href="list?page=1">◀◀</a>	
			<%
		}
		if(endPage > totalPage)
		{
			endPage = totalPage;
		}
							
	    if(startPage > displayPage)
	    { 
		%>
			<a class="btn btn-outline" href="list?page=<%=startPage - 10%>">이전</a>
		<%
		}
	    
		for(int i=startPage; i <= endPage; i++){%>
				<a class= "btn btn-outline <%=cur_Page == i ? "btn-active" : "" %>" href="list?page=<%=i%>"><%=i %></a>
		<%}
		
		if(endPage < totalPage)
		{
		%>
			<a class="btn btn-outline" href="list?page=<%=startPage + 10 %>">다음</a>
		<%
		}
		if(cur_Page < totalPage) {
			%>
			<a class = "last_page btn btn-outline" href="list?page=<%=totalPage%>">▶▶</a>	
			<%
		}
		%>
	</div>
	<style type="text/css">	
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