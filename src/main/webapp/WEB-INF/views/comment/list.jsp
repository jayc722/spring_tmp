<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>






</head>
<body>
	<h1>댓글</h1>
	<div class="comment-list">
		<c:forEach items="${comment}" var="co"><!-- taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" 추가 안하면 c:foreach 색깔부터 다름 -->
			<div class="comment-writer">${co.co_me_id}</div><!-- 작성자 -->
			<div class="comment-content">${co.co_content}</div><!-- 내용 -->
			<div>
				<button class="btn btn-outline-success">답글</button>
				<button class="btn btn-outline-warning">수정</button>
				<button class="btn btn-outline-danger">삭제</button>
			
			</div>
		</c:forEach>
	</div>
	
	
		
		
</body>
</html>
