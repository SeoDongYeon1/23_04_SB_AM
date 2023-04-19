<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Article List</title>

<link rel="stylesheet" href="/resource/common.css" />
<script src="/resource/common.js" defer="defer"></script>

</head>
<body>
	<h1>게시글 목록</h1>
	
	<hr />
	
	<div>
		<div>번호: ${article.id }</div>
		<div>제목: ${article.title }</div>
		<div>내용: ${article.body }</div>
		<div>작성날짜: ${article.regDate }</div>
		<div>수정날짜: ${article.updateDate }</div>
		<div>작성자: ${article.memberId }</div>
	</div>

</body>
</html>