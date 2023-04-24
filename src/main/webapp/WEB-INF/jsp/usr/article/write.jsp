<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Write"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	<br />
	<br />
	<div style="text-align:center;">
		<div style="font-weight:bold; font-size: 17px;">
			게시글 작성
		</div>
		<form style="width: 550px; height: 620px; border:2px solid black; display: inline-block;  border-radius: 8px;" method= "post" action="doWrite">
			<br />
			<div style="display: inline-block ; text-align:left;">
				<input value= "${article.id }" type="hidden" name="id"/>
				<div style="font-size: 17px; font-weight: bold; ">
					제목
					<br />
					<input class="input input-bordered w-full max-w-xs" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 500px;" type="text" value="${article.title }" name="title"/>
				</div>
				<br />
				<div style="font-size: 17px; font-weight: bold;">
					내용
					<br />
					<textarea class="textarea textarea-bordered" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 500px; height: 300px;" name="body">${article.body }</textarea>
				</div>
				<br />
			</div>
			<br />
			<br />
			<div style="border-radius: 8px; display: inline-block; width: 200px;">
				<button class="btn btn-outline" style="padding: 0 40px; " type="submit">작성</button>
			</div>
		</form>
	</div>

<%@ include file="../common/foot.jspf" %>