<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Modify"/>
<%@ include file="../common/head.jspf" %>
	<hr />
	
	<div style="text-align:center;">
		<div style="font-weight:bold; font-size: 17px;">
			${article.id }번 게시글 수정
		</div>
		<form method= "post" action="doModify" onsubmit="ArticleModify__submit(this) return false;" style="width: 550px; height: 620px; border:2px solid black; display: inline-block;  border-radius: 8px;" >
			<br />
			<div style="display: inline-block; text-align:left;">
				<input value= "${article.id }" type="hidden" name="id"/>
				<div style="text-align: right; font-size:14px; font-weight:bold">
					작성날짜 : ${article.regDate }
					<br />
					수정날짜 : ${article.updateDate }
					<br />
					작성자 : ${article.extra__writer }
				</div>
				<div style="font-size: 17px; font-weight: bold; ">
					제목
					<br />
					<input class="input input-bordered w-full max-w-xs" style="border-radius: 8px; width: 500px; border: 2px solid black;" type="text" value="${article.title }" name="title"/>
				</div>
				<br />
				<div style="font-size: 17px; font-weight: bold;">
					내용
					<br />
					<textarea class="textarea textarea-bordered" style="border: 2px solid black; border-radius: 8px; width: 500px; height: 300px;" name="body">${article.body }</textarea>
				</div>
				<br />
			</div>
			<br />
			<br />
			<div style="border-radius: 8px; display: inline-block; width: 200px;">
				<button class="btn btn-outline" style="padding: 0 40px; " type="submit">수정하기</button>
			</div>
		</form>
	</div>

<script>
let ArticleModify__submitDone = false; 
	
function ArticleModify__submit(form) {
    var title = form.title.value.trim();		
    var body = form.body.value.trim();
    
    if(title.length == 0) {
        alert('제목을 입력해주세요.');
        form.title.focus();
        return false;
    }
    if(body.length == 0) {
        alert('내용을 입력해주세요.');
        form.body.focus();	
        return false;
    }
    
    ArticleModify__submitDone = true;
    form.submit();
}
</script>
	
<%@ include file="../common/foot.jspf" %>