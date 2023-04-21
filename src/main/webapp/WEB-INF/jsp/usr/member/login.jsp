<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="LOGIN"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	<br />
	<br />
	<div style="text-align:center;">
		<div style="font-weight:bold; font-size: 17px;">
			로그인
		</div>
		<form style="width: 400px; height: 300px; border:2px solid black; display: inline-block;  border-radius: 8px;" method= "post" action="doLogin">
			<br />
			<div style="display: inline-block; text-align:left;">
				<div style="font-size: 17px; font-weight: bold; ">
					아이디
					<br />
					<input style="border-radius: 8px; width: 200px; border: 2px solid black;" type="text" placeholder="아이디" name="loginId" autocomplete="on" required/>
				</div>
				<br />
				<div style="font-size: 17px; font-weight: bold;">
					비밀번호
					<br />
					<input style="border-radius: 8px; width: 200px; border: 2px solid black;" type="password" placeholder="비밀번호" name="loginPw" autocomplete="off" required/>
				</div>
				<br />
			</div>
			<br />
			<br />
			<div style="border-radius: 8px; border: 2px solid black; display: inline-block; width: 200px;">
				<button style="padding:0 73px;" type="submit">로그인</button>
			</div>
		</form>
	</div>
	
<%@ include file="../common/foot.jspf" %>