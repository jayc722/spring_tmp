<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs4.min.js"></script>
</head>
<body>

	<h1>게시글 상세</h1>
		<div class="form-group">
			<label>게시판</label>
			<div class="form-control">${post.po_bo_name}</div>
		</div>
		<div class="form-group">
			<label>제목</label>
			<div class ="form-control">${post.po_title}</div>
		</div>
		<div class="form-group">
			<label>작성자</label>
			<div class ="form-control">${post.po_me_id}</div>
		</div>
		<div class="form-group">
			<label>작성일</label>
			<div class ="form-control">
				<fmt:formatDate value = "${post.po_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="form-group">
			<label>조회수</label>
			<div class ="form-control">${post.po_view}</div>
		</div>
		<div class="form-group">
			<label>내용</label>
			<div class ="form-control" style="min-height: 400px;">${post.po_content}</div>
		</div>
		
		<c:if test="${file.size() ne 0}">
			<div class="form-group">
				<label>첨부파일</label>
				<c:forEach items="${file}" var="file">
					<a class="form-control" href="<c:url value="/download${file.fi_name}"/>" download="${file.fi_ori_name}">${file.fi_ori_name }</a>
				</c:forEach>							
			</div>
		</c:if>
		
		<div class="mb-3 d-flex justify-content-between">
			<a href="<c:url value = "/post/list?bo_num=${post.po_bo_num}"/>" class="btn btn-outline-success">목록으로</a>
			<div>
				<c:if test="${post.po_me_id eq user.me_id}"><!-- 작성자에게만 보이도록 -->
				<a href="<c:url value="/post/update/${post.po_num}"/>" class="btn btn-outline-warning">수정</a>
				<a href="<c:url value="/post/delete/${post.po_num}"/>" class="btn btn-outline-danger">삭제</a>
				</c:if>
			</div>
		</div>
</body>
</html>
