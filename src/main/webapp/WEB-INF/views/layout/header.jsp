
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
<title>테스트</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

</head>
<body style="min-height: 100%">

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark ">
		<div class="container-fluid">
			<a class="navbar-brand d-flex justify-content-center" href="/" style="text-align: right;"><h2>테스트</h2></a>
		<div>
				<c:choose>
					<c:when test="${empty sessionScope.principal}">
						<ul class="navbar-nav justify-content-end">
							<li class="nav-item"><a class="nav-link"
								href="/user/loginForm">로그인</a></li>
							<li class="nav-item"><a class="nav-link"
								href="/user/joinForm">회원가입</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="navbar-nav justify-content-end">
							<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</nav>
	<br>