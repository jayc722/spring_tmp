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

		
		<div class="comment-container">
			<div class="comment-wrap"><!-- 댓글 목록 -->
			

			</div>
			<form class="input-group mt-3 comment-insert-form">
				<textarea class="form-control" name="content" placeholder="댓글 입력"></textarea>
				<button class="btn btn-outline-success">등록</button>
			</form>
		</div>
		
		<script type="text/javascript">

			var cri = {//현재 페이지정보
				page : 1,				//1페이지에서 시작.
				po_num : "${post.po_num}"	//input hidden에다 해도 되긴함
			}
			//$(".comment-insert-form").click(function(e){
			$(document).off("submit", ".comment-insert-form")
			$(document).on("submit", ".comment-insert-form", function(e){
				
				e.preventDefault();
				var $content = $(this).find("[name=content]");
				var content = $content.val().trim();
				var ori_num = $(this).data("num");
				ori_num = (typeof ori_num === 'undefined' || ori_num == null) ? 0 : ori_num;				//이거 추가해서 답글등록 댓글등록 같은 함수로 쓰기 위해서
						//Mapper에서 
						/*
						<if test="comment.co_ori_num == 0">ifnull(max(co_num), 0) + 1</if>에서 ori_num이 0이면 co_num이 ori_num으로 들어가게 했기때문에
						*/
				
				//댓글 내용 입력 안한 경우 처리
				if(content.length == 0){
					alert("댓글 내용을 입력하세요");
					$content.focus();
					return;
				}
				
				
				//alert(1);

				
				//console.log(co_content);

				//json으로 화면에서 보내서 서버에서 object로 받도록(노션의 json json에서 가져옴)
			
				$.ajax({
					async : true, //비동기 : true(비동기)
					url : '<c:url value="/comment/insert"/>',					//ajax 예제 참고 
					type : 'post', 													//json으로 보내는건 무조건 postmapping 해야함
					//data : JSON.stringify(obj), 
					data : JSON.stringify({
						co_po_num : cri.po_num,
						co_content : content,
						co_ori_num : ori_num
					}), 
					contentType : "application/json; charset=utf-8",	//json으로 보내니 필요
					//dataType : "json",								//object로 받으니 지움 
					success : function (data){
						console.log(data);
						//console.log(data);
						if(data){
							alert("댓글을 등록했습니다.");
							//댓글 입력 후 입력칸 공백으로
							//$(this).find("[name=content]").val("");				//여기의 this는 .ajax가 됨...
							$content.val('');										//이렇게 가져와야 함
							//댓글창 새로고침
							getCommentList(cri);				
						}else{
							alert("댓글을 등록하지 못했습니다.");
						}
					}, 
					error : function(jqXHR, textStatus, errorThrown){

					}
				});
			
			});
		
		</script>
		
		<script type="text/javascript">


		//console.log(cri);	
		
		//재사용 하려고 함수로 만들기
		getCommentList(cri);
		function getCommentList(cri){
			$.ajax({
				async : true, 
				url : '<c:url value="/comment/list"/>', 
				type : 'post',	//json으로 보내면 무조건 post... object로 보내면 get이든 post이든 상관x 
				data : JSON.stringify(cri), 
				contentType : "application/json; charset=utf-8",
				success : function (data){
					console.log(data);
					$(".comment-wrap").html(data);			//화면으로 보냈기 때문에 html코드 이용하면 html바로 적용됨
				}, 
				error : function(jqXHR, textStatus, errorThrown){

				}
			});
		
		
		}
		
		
		
		</script>
		
		
</body>
</html>
