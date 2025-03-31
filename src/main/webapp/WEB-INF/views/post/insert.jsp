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

	<form action="<c:url value="/post/insert"/>" method="post" enctype="multipart/form-data">
		<h1>게시글 등록</h1>
		<div class="form-group">
		  <label for="title">제목:</label>
		  <input type="text" class="form-control" id="title" name="po_title">
		</div>
		<div class="form-group">
		  <label for="board">게시판:</label>
		  <select class="form-control" id="board" name="po_bo_num">
		  	<c:forEach items="${boardList}" var="board">
		  		<option value="${board.bo_num}" <c:if test="${board.bo_num eq bo_num}">selected</c:if>>
		  			"${board.bo_name}"
		  		</option>
		  	</c:forEach>
		  </select>
		</div>
		<div class="form-group">
			<label for="content">내용:</label>
			<textarea class="form-control" id="content" name="po_content"></textarea>
		</div>
			
		<div class="form-group">
			<label>첨부파일(최대 10mb)</label>
			<input type="file" class="form-control" name="fileList"/><!-- 여기 name은 아무거나 상관없는데 files만 아니면 됨(썸머노트랑 겹침) -->
			<input type="file" class="form-control" name="fileList"/>
			<input type="file" class="form-control" name="fileList"/>
		</div>
		
		<button type="submit" class="btn btn-outline-success col-12 mb-3">게시글 등록</button>
	
		
	</form>
	 <script>
      $('[name=po_content]').summernote({
        placeholder: '글을 입력해 주세요.',
        tabsize: 2,
        height: 100
      });
      $("form").submit(function(e){
    	  let obj = $("[name=po_title]");
    	  let title = $("[name=po_title]").val().trim();
    	  if(title.length == 0){
    		  alert("제목을 입력하세요.");
    		  obj.focus();
    		  return false;
    	  }
      })
    </script>
</body>
</html>
