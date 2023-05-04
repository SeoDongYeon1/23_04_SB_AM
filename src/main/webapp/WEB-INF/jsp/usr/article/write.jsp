<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.sdy.demo.vo.Article"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Article Write"/>
<%@ include file="../common/head.jspf" %>
<%@ include file="../common/toastUIEditorLib.jspf"%>

<!-- Article write 관련 -->
<script type="text/javascript">
	$(document).ready(function() {
	    $('#boardId').on('change', function() {
	        $('input[name="boardId"]').val($(this).val());
	    });
	});

let ArticleWrite__submitDone = false; 
	
function ArticleWrite__submit(form) {
    var title = form.title.value.trim();		
    var body = form.body.value.trim();
    var boardId = $('input[name="boardId"]').val().trim();
    
    if(boardId.length == 0) {
        alert('게시판을 선택해주세요.');
        return false;
    }
    
    if(title.length == 0) {
        alert('제목을 입력해주세요.');
        form.title.focus(); 
        return false;
    }
    
	const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
	const markdown = editor.getMarkdown().trim();
	
	if (markdown.length == 0) {
		alert('내용을 입력해주세요.');
		editor.focus();
		return false;
	}

	form.body.value = markdown;
    
    ArticleWrite__submitDone = true;
    form.submit();
}
</script>


	<div style="text-align:center;">
		<div style="font-weight:bold; font-size: 17px;">
			게시글 작성
		</div>
		<form style="width: 850px; height: 1000px; border:2px solid black; display: inline-block;  border-radius: 8px;" method= "post" action="doWrite" onsubmit = "return ArticleWrite__submit(this); return false;">
			<br />
			<input type="hidden" name="body">
			<div style="text-align: left; margin: 0 25px; font-weight: bold;">
				작성자: ${rq.loginedMember.nickname }
			</div>
			<br />
			<div style="margin-left:25px; text-align: left; ">
				<select style="border-color: black;" id="boardId" class="select select-ghost w-full max-w-xs">
				  <option disabled selected>게시판 선택</option>
					<option class="notice" value="1">공지사항</option>
					<option class="free" value="2">자유게시판</option>
					<option class="qna" value="3">QnA</option>
				</select>
			</div>
			<br />
			<div style="display: inline-block ; text-align:left;">
				<input class="boardId" type="hidden" name="boardId"/>
				<div style="font-size: 17px; font-weight: bold; ">
					제목
					<br />
					<input class="title input input-bordered w-full max-w-xs" style="border: 2px solid black; border-radius: 8px; border-color:black; width: 500px;" type="text" name="title"/>
				</div>
				<br />
				<div style="font-size: 17px; font-weight: bold;">
					내용
					<br />
					<div style="width: 800px;" class="toast-ui-editor">
						<script type="text/x-template"></script>
					</div>
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