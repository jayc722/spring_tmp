<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>






</head>
<body>
	<h1>댓글</h1>
	<c:if test="${not empty comment}">
		<div class="comment-list">
			<c:forEach items="${comment}" var="co">	<!-- taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" 추가 안하면 c:foreach 색깔부터 다름 -->
													<!-- co 변수로 사용됨 -->
				<div class="comment-item <c:if test="${co.co_num ne co.co_ori_num }">pl-5</c:if>">
					<c:if test="${co.co_del eq 'N'}">					
						<div class="comment-writer">${co.co_me_id}</div><!-- 작성자 -->
						<div class="comment-content">${co.co_content}</div><!-- 내용 -->
						<div>
							<button class="btn btn-outline-success comment-reply" data-num="${co.co_num}">답글</button>	<!-- co_ori_num이 될 값을 여기에 추가 -->
							<button class="btn btn-outline-warning comment-update" data-num="${co.co_num}">수정</button>
							<button class="btn btn-outline-danger comment-delete" data-num="${co.co_num}">삭제</button>
						</div>
					</c:if>
					<c:if test="${co.co_del ne 'N'}">
						<div class="mt-5">작성자에 의해 삭제된 댓글입니다.</div>
					</c:if>
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
	
	
	<script type="text/javascript">
		$(document).off("click",".comment-reply");
		$(document).on("click",".comment-reply", function(e){
			//alert("답글");
			let co_ori_num = $(this).data("num"); 
			$(this).closest('.comment-list').find('.reply').remove();
			const replyHtml = `
				<div class="reply">
					<form class="input-group mt-3 comment-insert-form" data-num="\${co_ori_num}">
						<textarea class="form-control" name="content" placeholder="답글 입력"></textarea>
						<button class="btn btn-outline-success">등록</button>
					</form>
				</div>
				`;

			$(this).parent().after(replyHtml);
		});
	
	</script>
		
	<script type="text/javascript">
		$(document).off("click",".comment-update")
		$(document).on("click",".comment-update",function(e){
			let $content = $(this).closest(".comment-item").find(".comment-content");
			//let $content = $(this).parent().siblings(".comment-content");
			//let $content = $(this).parents(".coment-item").find(".comment-content");		//강사님. 
			
			let content = $content.html();
			
			//alert(content);
			
			$content.hide();		//j쿼리에서 제공하는 메소드
			
			//if($content.nextAll(".comment-update-form".length != 0)) return;
			
			let num = $(this).data("num");
			const str = `
				<div class="update">
					<form class="input-group mt-3 comment-insert-form" data-num="\${num}">
						<textarea class="form-control" name="content" placeholder="수정할 내용 입력">\${content}</textarea>
						<button class="btn btn-outline-success" type="submit">수정</button>
					</form>
				</div>
				`;
				
			$(this).parents('.comment-container').find('.comment-update').parent().show();			
			$(this).closest('.comment-wrap').find('.update').remove();
			$(this).parent().hide();			//수정버튼 누르면 버튼입력창 감춰지게 하면 수정버튼 여러번 누를수 없어서 수정버튼 여러번 누를때 굳이 안만들어도 되긴함..
			$content.after(str);
		});
	
	
	</script>
		
	
	
	<script type="text/javascript">
	$(document).off("click",".comment-delete");
	$(document).on("click",".comment-delete", function(e){
		alert("삭제");
		getCommentList(cri);
		let num = $(this).data("num");
		
		$.ajax({
			async : true, //비동기 : true(비동기)
			url : '<c:url value="/comment/delete"/>',					
			type : 'post', 													
			//data : JSON.stringify(obj), 
			data: { co_num: num },
			//contentType : "application/json; charset=utf-8",	//object
			//dataType : "json",								//object 
			success : function (data){

			console.log(data);
			}, 
			error : function(jqXHR, textStatus, errorThrown){

			}
		});
	
	});


	
	</script>
	
	
	<script type="text/javascript"> //detail 댓글입력 가져옴
	/*	//만들었는데 필요없네... detail에서 댓글 등록 때 썼던거 그대로쓰면 돼서...
			
			//$(".comment-insert-form").click(function(e){
			$(document).off("submit", ".reply-insert-form")
			$(document).on("submit", ".reply-insert-form", function(e){
				
				e.preventDefault();
				var $content = $(this).find("[name=content]");
				var content = $content.val().trim();
				
				
				
				//댓글 내용 입력 안한 경우 처리
				if(content.length == 0){
					alert("댓글 내용을 입력하세요");
					$content.focus();
					return;
				}
				
				
				//alert(1);

				
				console.log(content);

				//json으로 화면에서 보내서 서버에서 object로 받도록(노션의 json json에서 가져옴)
			
				$.ajax({
					async : true, //비동기 : true(비동기)
					url : '<c:url value="/comment/reply"/>',					//ajax 예제 참고 
					type : 'post', 													//json으로 보내는건 무조건 postmapping 해야함
					//data : JSON.stringify(obj), 
					data : JSON.stringify({
						co_po_num : cri.po_num,
						co_content : content,
						co_ori_num : co_ori_num
					}), 
					contentType : "application/json; charset=utf-8",	//json으로 보내니 필요
					//dataType : "json",								//object로 받으니 지움 
					success : function (data){
						console.log(data);
						//console.log(data);
						if(data){
							alert("답글을 등록했습니다.");
							//댓글 입력 후 입력칸 공백으로
							//$(this).find("[name=content]").val("");				//여기의 this는 .ajax가 됨...
							$content.val('');										//이렇게 가져와야 함
							//댓글창 새로고침
							getCommentList(cri);				
						}else{
							alert("답글을 등록하지 못했습니다.");
						}
					}, 
					error : function(jqXHR, textStatus, errorThrown){

					}
				});
			
			});

			*/
	</script>
		
		
</body>
</html>
