<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Member"%>

<%
int totalPage = (int) request.getAttribute("totalPage");
int cur_Page = (int) request.getAttribute("page");

int displayPage = 10;
int startPage = ((cur_Page-1)/displayPage)*displayPage+1;
int endPage = startPage+displayPage-1;
%>

<c:set var="pageTitle" value="Admin Page" />
<%@ include file="../common/head.jspf"%>


<div class="mt-8 text-xl mx-auto px-3">
		<div style="text-align:center;">전체 회원 수 : ${membersCount }개</div>
		<table class="table-box-type-1 table table-zebra w-full" style="text-align:center;">
			<tr>
				<th>회원번호</th>
				<th>가입날짜</th>
				<th>수정날짜</th>
				<th>로그인 아이디</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>전화번호</th>
				<th>이메일</th>
				<th>회원 등급</th>
				<th>탈퇴 여부</th>
				<th>탈퇴 날짜</th>
			</tr>
		<c:forEach var="member" items="${members } }">
			<tr>
				<th><div class="badge badge-outline">${member.id }</div></th>
				<th>${member.regDate.substring(0,10) }</th>
				<th>${member.updateDate }</th>
				<th>${member.loginId }</th>
				<th>${member.name }</th>
				<th>${member.nickname }</th>
				<th>${member.cellphoneNum }</th>
				<th>${member.email }</th>
				<th>${member.authLevel }</th>
				<th>${member.delStatus }</th>
				<th>${member.delDate }</th>
			</tr>
		</c:forEach>
		</table>
</div>

<div class="pagenation" style="text-align: center; margin-top:20px;">
		<c:set var="baseUri" value="?searchKeywordTypeCode=${searchKeywordTypeCode }" />
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
	<form action="">
		<div style="text-align: center; margin-top: 20px;">
			<div style="display: inline-block; ">
				<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode" class="select select-ghost">
					<option disabled selected>검색 설정</option>
					<option value="loginId">제목</option>
				</select>
			</div>
		<div style="display: inline-block;">
			<div style="font-size: 17px; font-weight: bold; ">
				<input value="${param.searchKeyword }" maxlength="20" name="searchKeyword" class="input input-bordered" type="text" placeholder="검색어를 입력해주세요" />
			</div>
		</div>
		<div style="border-radius: 8px; display: inline-block;">	
			<button class="btn btn-ghost" onclick="Search() return false;" type = "submit">검색</button>
		</div>
		</div>
	</form>
	
	<style type="text/css">	
	body {
	  height: 1000px;
	}
	.title:hover {
		text-decoration: underline;
	}
	
	.table-box-type-1 {
		margin-left: auto;
		margin-right: auto;
		width: 800px;
	}
	</style>

<%@ include file="../common/foot.jspf"%>