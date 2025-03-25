<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	
</head>
<body>
	<h1>게시글 목록</h1>
		    ${list }
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
			        <td>
			        	<a href="">${post.po_title}</a>
			        </td>
		    	    <td>${post.po_me_id}</td>
			        <td>
			        	<fmt:formatDate value="${post.po_date}" pattern="yyyy-MM-dd"/>
			        </td>
			        <td>${post.po_view}</td>
		    	    <td>${post.po_up}/${post.po_down}</td>
			      </tr>
			    </c:forEach>
		   </tbody>
	  </table>


</body>
</html>
