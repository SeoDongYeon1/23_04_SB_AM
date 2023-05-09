<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>

<script>
	let submitJoinFormDone = false;
	let validLoginId = "";

	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value != validLoginId) {
			alert('사용할 수 없는 아이디입니다.');
			form.loginId.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요');
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요');
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			return;
		}
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요');
			return;
		}
		submitJoinFormDone = true;
		form.submit();
	}

	function checkLoginIdDup(el) {
		const form = $(el).closest('form').get(0);

		if (form.loginId.value.length == 0) {
			validLoginId = "";
			return;
		}
		
		if (form.loginId.value == validLoginId) {
			return;
		}

		if (form.loginId.value.length >= 5) {
			var action = "../member/getLoginIdDup";
			$.get(action, {

				isAjax : 'Y',
				loginId : form.loginId.value

			}, function(data) {
				$('.checkDup-msg').text(data.msg);

				if (data.success) {
					validLoginId = data.data1;
				} else {

					validLoginId = "";
				}

			}, 'json');
		}
	}
</script>

<section class="mt-8 text-xl form-box">
		<div class="container mx-auto px-3">
				<form class="table-box-type-1" method="POST" action="../member/doJoin"
						onsubmit="submitJoinForm(this); return false;">
						<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
						<table class="table table-zebra w-full">
								<colgroup>
										<col width="200" />
								</colgroup>

								<tbody>
										<tr>
												<th>아이디</th>
										</tr>
										<tr>
												<td><input onkeyup="checkLoginIdDup(this);" autocomplete="off" style="display: inline-block;"
														name="loginId" class="w-full input input-bordered  max-w-xs" placeholder="아이디를 입력해주세요" />
														<div class="checkDup-msg"></div></td>
										</tr>
										<tr>
												<th>비밀번호</th>
										</tr>
										<tr>
												<td><input name="loginPw" class="w-full input input-bordered  max-w-xs" placeholder="비밀번호를 입력해주세요" />
												</td>
										</tr>
										<tr>
												<th>비밀번호 확인</th>
										</tr>
										<tr>
												<td><input name="loginPwConfirm" class="w-full input input-bordered  max-w-xs"
														placeholder="비밀번호 확인을 입력해주세요" /></td>
										</tr>
										<tr>
												<th>이름</th>
										</tr>
										<tr>
												<td><input name="name" class="w-full input input-bordered  max-w-xs" placeholder="이름을 입력해주세요" /></td>
										</tr>
										<tr>
												<th>닉네임</th>
										</tr>
										<tr>
												<td><input name="nickname" class="w-full input input-bordered  max-w-xs" placeholder="닉네임을 입력해주세요" />
												</td>
										</tr>
										<tr>
												<th>전화번호</th>
										</tr>
										<tr>
												<td><input name="cellphoneNum" class="w-full input input-bordered  max-w-xs" placeholder="전화번호를 입력해주세요" />
												</td>
										</tr>
										<tr>
												<th>이메일</th>
										</tr>
										<tr>
												<td><input name="email" class="w-full input input-bordered  max-w-xs" placeholder="이메일을 입력해주세요" /></td>
										</tr>
										<tr>
												<td>
														<button class="btn btn-active btn-ghost" type="submit" value="회원가입">회원가입</button>
												</td>
										</tr>
								</tbody>

						</table>
				</form>
		</div>

		<div class="container mx-auto btns">
				<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>

</section>
<style>
	form {
		width: 500px;
	}
</style>

<%@ include file="../common/foot.jspf"%>