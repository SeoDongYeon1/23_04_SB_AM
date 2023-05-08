<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AjaxTest Page</title>
<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<style>
.rs {
	border: 5px solid black;
	margin-top: 30px;
	padding: 20px;
	font-size: 2rem;
}
</style>
<script>
	function callByAjax() {
		// 문서의 form1을 가져옴
		var form = document.form1;
		
		var num1 = form.num1.value; 
		var num2 = form.num2.value;
		var action = form.action;
		
		$.get(action, {
			// = 대신 : 사용
			num1 : num1,
			num2 : num2
		}, function(data) {
			// empty는 보여진 이전의 값을 지운다.
			//$('.rs').empty().append(data);
			
			// empty 하고 하는거랑 같은 의미
			$('.rs').text(data);
			
		}, 'html');
	}
</script>
</head>
<body>
		<h1>Ajax Test</h1>

		<form name="form1" method="POST" action="./doPlus">
				<div>
						<input type="text" name="num1" placeholder="정수 입력" />
				</div>
				<div>
						<input type="text" name="num2" placeholder="정수 입력" />
				</div>
				<div>
						<input type="submit" value="더하기" />
						<input onclick="callByAjax();" type="button" value="더하기v2" />
				</div>
		</form>

		<h2>더한 결과</h2>

		<div class="rs"></div>
</body>
</html>