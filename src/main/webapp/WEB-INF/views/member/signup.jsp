<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/additional-methods.min.js"></script>
	

</head>
<body>

	<h1 class = "mt-3">회원 가입</h1>
	<form action="<c:url value="/signup"/>" method="post">
		
		<div class="form-group">
		  <label for="id">아이디:</label>
		  <input type="text" class="form-control" id="id" name="me_id" value="${id}">
		</div>
		<button type="button" class="btn btn-outline-success col-12" id="check">아이디 중복 확인</button>
		<!-- 버튼 타입은 기본이 submit이라 타입 안 바꾸면 회원가입 넘어가져버림 -->
		
		<div class="form-group">
		  <label for="pw">비밀번호:</label>
		  <input type="password" class="form-control" id="pw" name="me_pw">
		</div>
		<div class="form-group">
		  <label for="pw2">비밀번호 확인:</label>
		  <input type="password" class="form-control" id="pw2" name="me_pw2">
		</div>
		<div class="form-group">
		  <label for="email">이메일:</label>
		  <input type="email" class="form-control" id="email" name="me_email">
		</div>
		<button type="submit" class="btn btn-outline-success col-12 mb-3">회원가입</button>
	
	</form>
	<script type="text/javascript">
		$("#check").click(function(e){	//j쿼리 방식
			//alert(1);

			if(checkId()){
				alert("사용 가능한 아이디입니다.");
			}else{
				alert("이미 사용중인 아이디입니다.");
			}
		});	
		function checkId(){			// 유효성검사에도 필요한 기능이기 때문에 외부에 함수로 만들어서 나중에 재사용 가능하게
			// 입력한 아이디를 가져옴
			let id = $("#id").val();
			// 정규표현식에 맞지 않으면 알림창 리턴 후 종료(차후 도입)
			
			let res = false;		
			//비동기 통신으로 아이디를 전송하고, 서버에서 보낸 결과를 이용하여 처리
			$.ajax({	//여기는 j쿼리 코드
				async : false, // true(비동기), false(동기)	//중복검사 마친 뒤에 회원가입 해야 하니 동기로 
				//비동기로 하면 이거 이뤄지기 전에 다음꺼 해버리니 무조건 있는 아이디라고 출력됨
				url : '<c:url value="/check/id"/>', 
				type : 'post', 
				data : { id : id }, 
				success : function (data){
					if(data){
						res = true;
					}
				}, 
				error : function(jqXHR, textStatus, errorThrown){
				}
			});
			return res;
		}
		$("form").validate({			//23.회원가입_validate 가져옴
			rules : {
				me_id : {
					required : true,
					regex : /^[a-zA-Z0-9]{3,13}$/ 

				},	
				me_pw : {	
					required : true,
					regex : /^[a-zA-Z0-9!@#$]{8,15}$/ 
				},
				me_pw2 : {
					equalTo : pw		// 아이디값이라 me_pw아니라 pw(name은 중복될 수 있지만 id는 하나만 있을수 있어서)
				}
			},	
			messages : {
				me_id : {
					required : "필수 항목입니다.",
					regex : "아이디는 영문, 숫자만 가능하며, 3~13자입니다."
				},
				me_pw : {
					required : "필수 항목입니다.",
					regex : "비번은 영문, 숫자, 특수문자(!@#$)만 가능하며, 8~15자입니다."
				},
				me_pw2 : {
					equalTo : "비번과 비번확인이 일치하지 않습니다."
				}

			},
			submitHandler : function(){	//submitHandler : 유효성 검사 후 전송하기 직전 확인하고 싶을때 : return true여야 전송.
				return true;
			}
		})
		$.validator.addMethod("regex", function(value, element, regex){
			var re = new RegExp(regex);	//정규표현식 생성자 RegEXP
			return this.optional(element) || re.test(value);
		}, "정규표현식을 확인하세요.")
		
		
	</script>
	
</body>
</html>
