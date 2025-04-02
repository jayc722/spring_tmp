<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>






</head>
<body>
	<h1>댓글</h1>
	<c:if test="${comment.size()!=0}">
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
		
		<div class="comment-pagination mt-3"><!-- w3school 샘플코드 복붙 -->
		  <ul class="pagination justify-content-center">
	  		<c:if test="${!page.prev}"><!-- post/list.jsp에서 복붙 -->
				<c:set var="prev" value="disabled" />
				<!-- 이전버튼 비활성화 시키기(c:set 변수선언) -->
			</c:if>
		    <li class="page-item ${prev}">
		    	<a class="page-link" href="javascript:void(0);" data-page="${page.startPage - 1}">이전</a>
		    </li>
		    <c:forEach begin="${page.startPage}" end="${page.endPage}" var="num">
	 			<c:set var="active" value="" /><!-- post/list.jsp에서 복붙 -->
				<c:if test="${page.cri.page == num}">
					<c:set var="active" value="active" />
				</c:if>
			    <li class="page-item ${active}">
			    	<a class="page-link" href="javascript:void(0);" data-page="${num}">${num}</a>
			    </li>
		   </c:forEach>
			    
	   		<c:if test="${!page.next}"><!-- list.jsp에서 복붙 -->
				<c:set var="next" value="disabled" />
				<!-- 다음버튼 비활성화 시키기(c:set 변수선언) -->
			</c:if>
		    <li class="page-item ${next}">
		    	<a class="page-link" href="javascript:void(0);" data-page="${page.endPage + 1}">다음</a>
		    </li>
	
		  </ul>	
		</div>

	</c:if>	
	<c:if test="${comment.size() == 0}">
		<div class="text-center">등록된 댓글이 없습니다.</div>
	</c:if>
	
	
	<script type="text/javascript">
		$(".comment-pagination .page-link").click(function(e){
			cri.page = $(this).data("page");
			getCommentList(cri);
		})
	</script>
	
	
		
		
</body>
</html>
