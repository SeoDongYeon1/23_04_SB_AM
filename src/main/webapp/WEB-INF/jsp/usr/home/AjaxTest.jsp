<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AjaxTest Page</title>
<style>
.rs {
	border: 5px solid black;
	margin-top: 30px;
	padding: 20px;
	font-size: 2rem;
}
</style>
</head>
<body>
		<h1>Ajax Test</h1>

		<form method="POST" action="./doPlus">
				<div>
						<input type="text" name="num1" placeholder="정수 입력" />
				</div>
				<div>
						<input type="text" name="num2" placeholder="정수 입력" />
				</div>
				<div>
						<input type="submit" value="더하기" />
						<input type="submit" value="더하기v2" />
				</div>
		</form>

		<h2>더한 결과</h2>

		<div class="rs"></div>
</body>
</html>