<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

</head>
<body>
	<div class="board-list mb-3 mt-3">

		<a href="<c:url value='/post/list'>
		            <c:param name='bo_num' value='0'/>
		            <c:param name='type' value='${pageMaker.cri.type}'/>
		            <c:param name='search' value='${pageMaker.cri.search}'/>
		         </c:url>"
		   class="btn ${pageMaker.cri.bo_num ne 0 ? 'btn-outline-success' : 'btn-success'}">
		   전체
		</a>
		<c:forEach items="${boardList}" var="board">
		  <a href="<c:url value='/post/list'>
		             <c:param name='bo_num' value='${board.bo_num}'/>
		             <c:param name='type' value='${pageMaker.cri.type}'/>
		             <c:param name='search' value='${pageMaker.cri.search}'/>
		           </c:url>"
		     class="btn ${pageMaker.cri.bo_num ne board.bo_num ? 'btn-outline-primary' : 'btn-primary'}">
		    ${board.bo_name}
		  </a>
		</c:forEach>
	</div>

	<h1>게시글 목록</h1>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>추천/비추</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="post">
				<tr>
					<td>${post.po_num}</td>
					<td><a href="<c:url value="/post/detail/${post.po_num}"/>">${post.po_title}</a>
					</td>
					<td>${post.po_me_id}</td>
					<td><fmt:formatDate value="${post.po_date}"
							pattern="yyyy-MM-dd" /></td>
					<td>${post.po_view}</td>
					<td>${post.po_up}/${post.po_down}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="6" class="text-center">등록된 게시글이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<ul class="pagination justify-content-center">
		<c:if test="${!pageMaker.prev}">
			<c:set var="prev" value="disabled" />
			<!-- 이전버튼 비활성화 시키기(c:set 변수선언) -->
		</c:if>

		<c:url var="url" value="/post/list">
			<c:param name="bo_num" value="${pageMaker.cri.bo_num}" />
			<c:param name="page" value="${pageMaker.startPage - 1}" />
			<!-- 게시판 변경해도 검색어 남기려고 -->
			<c:param name="type" value="${pageMaker.cri.type}" />
			<c:param name="search" value="${pageMaker.cri.search}" />
			<!-- 게시판 변경해도 검색어 남기려고 -->
		</c:url>

		<li class="page-item ${prev}"><a class="page-link" href="${url}">이전</a>
		</li>

		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
			var="page">
			<c:set var="active" value="" />
			<c:if test="${pageMaker.cri.page == page}">
				<c:set var="active" value="active" />
			</c:if>
			<c:url var="url" value="/post/list">
				<c:param name="bo_num" value="${pageMaker.cri.bo_num}" />
				<c:param name="page" value="${page}" />
				<!-- 게시판 변경해도 검색어 남기려고 -->
				<c:param name="type" value="${pageMaker.cri.type}" />
				<c:param name="search" value="${pageMaker.cri.search}" />
				<!-- 게시판 변경해도 검색어 남기려고 -->				
			</c:url>
			<li class="page-item ${active}"><a class="page-link"
				href="${url}">${page}</a></li>
		</c:forEach>
		<c:if test="${!pageMaker.next}">
			<c:set var="next" value="disabled" />
			<!-- 다음버튼 비활성화 시키기 -->
		</c:if>
		<c:url var="url" value="/post/list">
			<c:param name="bo_num" value="${pageMaker.cri.bo_num}" />
			<c:param name="page" value="${pageMaker.endPage + 1}" />
			<!-- 게시판 변경해도 검색어 남기려고 -->
			<c:param name="type" value="${pageMaker.cri.type}" />
			<c:param name="search" value="${pageMaker.cri.search}" />
			<!-- 게시판 변경해도 검색어 남기려고 -->
		</c:url>
		<li class="page-item ${next}">
			<a class="page-link" href="${url}">다음</a>
		</li>
	</ul>
	
	
	<form action="<c:url value="/post/list"/>" class="input-group mb-3">
		<input type="hidden" value="${pageMaker.cri.bo_num}" name="bo_num"><!-- 검색어를 유지하기 위해 -->
		<select class="form-control" name="type"><!-- criteria에 type이라는 변수가 있어서 거기로 보낼거기 때문에 -->
			
			<c:set var="selected" value = ""/>
			<c:if test="${pageMaker.cri.type =='all'}">
				<c:set var="selected" value="selected" />
			</c:if>
			<option value="all" ${selected}>전체</option>
			
			<c:set var="selected" value = ""/>
			<c:if test="${pageMaker.cri.type =='title'}">
				<c:set var="selected" value="selected" />
			</c:if>
			<option value="title" ${selected}>제목</option>
			
			<c:set var="selected" value = ""/>
			<c:if test="${pageMaker.cri.type =='content'}">
				<c:set var="selected" value="selected" />
			</c:if>
			<option value="content" ${selected}>제목+내용</option>
			
			<c:set var="selected" value = ""/>
			<c:if test="${pageMaker.cri.type =='name'}">
				<c:set var="selected" value="selected" />
			</c:if>
			<option value="name" ${selected}>작성자</option>
			
		</select> 
		<input type="text" class="form-control" placeholder="검색어 입력" name="search" value="${pageMaker.cri.search }"><!-- criteria에 search라는 변수가 있어서 거기로 보낼거기 때문에 -->
		<button class="form-control btn btn-outline-success">검색</button>
	</form>


	<div class="clearfix mb-3">
		<!-- 높이 잡히게 하기 위해(푸터랑 안 겹치게 하기 위해) clearfix -->
		<a href="<c:url value="/post/insert?bo_num=${bo_num}"/>"
			class="btn btn-outline-success float-right">게시글 등록</a>
		<!-- 게시글 등록할때 일일히 선택 안해도 되게 보고있는 게시판 번호가 들어가게 -->
	</div>


</body>
</html>
