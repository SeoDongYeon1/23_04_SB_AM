<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js" referrerpolicy="no-referrer"></script>

<script>
	let MemberFindLoginId__submitDone = false;

	function MemberFindLoginId__submit(form) {
		if (MemberFindLoginId__submitDone) {
			alert('처리중입니다');
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();
			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}

		MemberFindLoginId__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl form-box">
		<div class="container mx-auto px-3">
				<form class="table-box-type-1" method="POST" action="../member/doFindLoginId"
						onsubmit="MemberFindLoginId__submit(this); return false;">
						<input type="hidden" name="afterFindLoginIdUri" value="${param.afterFindLoginIdUri }" />
						<table class="table table-zebra w-full">
								<colgroup>
										<col width="200" />
								</colgroup>

								<tbody>
										<tr>
												<th>이름</th>
										</tr>
										<tr>
												<td><input name="name" class="w-full input input-bordered  max-w-xs" placeholder="이름을 입력해주세요" /></td>
										</tr>
										<tr>
												<th>이메일</th>
										</tr>
										<tr>
												<td><input name="email" class="w-full input input-bordered  max-w-xs" placeholder="가입시 입력하신 이메일을 입력해주세요" /></td>
										</tr>
										<tr>
												<td>
														<button class="btn btn-outline" type="submit" value="아이디 찾기">아이디 찾기</button>
												</td>
										</tr>
								</tbody>

						</table>
				</form>
		</div>

		<div class="container mx-auto btns">
				<button class="btn-text-link btn btn-outline" type="button" onclick="history.back();">뒤로가기</button>
		</div>

</section>
<style>
	form {
		width: 500px;
	}
</style>

<%@ include file="../common/foot.jspf"%>