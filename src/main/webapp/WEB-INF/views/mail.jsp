<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="layout/header.jsp" %>

	<div class="container">
		<h1>메일 발송</h1>
		<form th:action="@{/mail}" method="post">
			<input class="form-control" name="address" placeholder="이메일 주소">
			<input class="form-control" name="title" placeholder="제목"> <br>
			<textarea id="summernote"  name="message" placeholder="메일 내용을 입력해주세요." cols="60" rows="20"></textarea>
			<button>발송</button>
		</form>
	</div>

<script>
	$('#summernote').summernote({
		placeholder : "내용을 입력하세요.(엔터 키를 누르면 크기가 늘어납니다.)"
	});
</script>

<%@ include file="layout/footer.jsp"%>