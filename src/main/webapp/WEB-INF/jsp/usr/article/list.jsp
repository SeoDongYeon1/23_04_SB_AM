<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Article List</title>
</head>
<body>
	<h1>LIST</h1>
		<header>
				<a href="/">로고</a>

				<ul>
						<li>
								<a href="/">HOME</a>
						</li>
						<li>
								<a href="../article/list">LIST</a>
						</li>
				</ul>
		</header>
	
	<hr />
	
	<div>
		<table style="border-collapse: collapse; width: 700px; " border = 2px >
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성날짜</th>
				<th>작성자</th>
			</tr>
			
		<c:forEach var="article" items="${articles }">
			<tr>
				<th>${article.id }</th>
				<th><a href="detail?id=${article.id }">${article.title }</a></th>
				<th>${article.regDate.substring(0,10) }</th>
				<th>${article.memberId }</th>
			</tr>

		</c:forEach>
		</table>
	</div>

</body>
</html>