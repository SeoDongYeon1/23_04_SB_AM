<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Ajax Test</title>
<style>
.rs {
	border: 5px solid black;
	margin-top: 3px;
	padding: 20px;
	font-size: 2rem;
}
.rs-msg {
	border: 5px solid green;
	margin-top: 3px;
	padding: 20px;
	font-size: 2rem;
}
.rs-code {
	border: 5px solid pink;
	margin-top: 3px;
	padding: 20px;
	font-size: 2rem;
}
</style>

<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<script>
	function callByAjaxV2() {
		var form = document.form1;
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		var action = form.action;
		$.get(action, {
			num1 : num1,
			num2 : num2
		}, function(data) {
			// empty는 보여진 이전의 값을 지운다.
			//$('.rs').empty().append(data);
			
			// empty 하고 하는거랑 같은 의미
			$('.rs').text(data);
		}, 'html');
	}
	function callByAjaxV3() {
		var form = document.form1;
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		var action = form.action;
		$.get(action, {
			num1 : num1,
			num2 : num2
		}, function(data) {
			data = data.split('/');
			var rs = data[0];
			var msg = data[1];
			$('.rs').text(rs);
			$('.rs-msg').text(msg);
		}, 'html');
	}
	function callByAjaxV4() {
		var form = document.form1;
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		var action = form.action;
		$.get(action, {
			num1 : num1,
			num2 : num2
		}, function(data) {
			data = data.split('/');
			var rs = data[0];
			var msg = data[1];
			var code = data[2];
			$('.rs').text(rs);
			$('.rs-msg').text(msg);
			$('.rs-code').text(code);
		}, 'html');
	}
	function callByAjaxV5() {
		var form = document.form1;
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		var action = './doPlusJson';
		$.get(action, {
			num1 : num1,
			num2 : num2
		}, function(data) {
			$('.rs').text(data.rs);
			$('.rs-msg').text(data.msg);
			$('.rs-code').text(data.code);
		}, 'json');
	}
</script>
</head>
<body>
	<h1>Ajax Test</h1>

	<form name="form1" method="get" action="./doPlus">
		<div>
			<input type="text" name="num1" placeholder="정수 입력" />
		</div>
		<div>
			<input type="text" name="num2" placeholder="정수 입력" />
		</div>
		<input type="submit" value="더하기" />
		<input onclick="callByAjaxV2();" type="button" value="더하기v2" />
		<input onclick="callByAjaxV3();" type="button" value="더하기v3" />
		<input onclick="callByAjaxV4();" type="button" value="더하기v4" />
		<input onclick="callByAjaxV5();" type="button" value="더하기v5" />
	</form>

	<h2>더한 결과</h2>
	<div class="rs"></div>

	<h2>더한 결과 메세지</h2>
	<div class="rs-msg"></div>

	<h2>더한 결과 코드</h2>
	<div class="rs-code"></div>
</body>
</html>
