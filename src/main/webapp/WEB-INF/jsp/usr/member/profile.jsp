<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Member Profile" />
<%@ include file="../common/head.jspf"%>


<div class="profile_box mt-8 text-xl">
		<table class="table-box-type-1 table table-zebra">
				<tr>
						<th>아이디</th>
						<th>${member.loginId }</th>
				</tr>

				<tr>
						<th>이름</th>
						<th>${member.name }</th>
				</tr>

				<tr>
						<th>닉네임</th>
						<th>${member.nickname }</th>
				</tr>
				
				<tr>
						<th>이메일</th>
						<th>${member.email }</th>
				</tr>
				
				<tr>
						<th>전화번호</th>
						<th>${member.cellphoneNum }</th>
				</tr>

				<tr>
						<th>가입날짜</th>
						<th>${member.regDate }</th>
				</tr>

		</table>
		<br />
		<div class="btn_box">
				<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
				<a class="btn btn-outline" href="../member/checkPw">회원정보 수정</a>
				<a class="btn btn-outline" onclick="if(confirm('정말로 회원탈퇴 하시겠습니까?')==false) return false;" href="../member/doDelete?id=${member.id }">회원탈퇴</a>
		</div>
</div>

<style type="text/css">
	.profile_box {
		margin-left: auto;
		margin-right: auto;
		width: 500px;
	}
	
	.btn_box {
		text-align: center;
	}
</style>

<%@ include file="../common/foot.jspf"%>