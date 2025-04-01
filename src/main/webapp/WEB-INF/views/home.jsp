<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
	<div id="res1"></div>
	<div id="res2"></div>
	<div id="res3"></div>
	<div id="res4"></div>
	<div id="res5"></div>
	<div id="res6"></div>
	<div id="res7"></div>
	<div id="res8"></div>

	<script type="text/javascript">
	
		let obj = {
				name : "홍길동", 
				age : 20				
		}		
	
		//1. object -> object		
		$.ajax({
			async : true, //true(비동기)
			url : '<c:url value="/ajax/sample1"/>', 			// 받아줄 애가 필요
			type : 'get', 
			data : obj, //{속성1 : 값1 , 속성2 : 값2}, 
			success : function (data){
				console.log(data);
				$("#res1").html(data);
			}, 
			error : function(jqXHR, textStatus, errorThrown){
	
			}
		});
	
		//2. json -> object		
		//JSON.stringify(객체) : 객체를 json 형태 문자열로 변환
		//JSON.parse(문자열)	: json 형태의 문자열을 객체로 변환
		$.ajax({
			async : true, //true(비동기)
			url : '<c:url value="/ajax/sample2"/>', 			
			type : 'post',										//json방식은 post방식으로 보내야 
			data : JSON.stringify(obj), //{속성1 : 값1 , 속성2 : 값2}, 
			contentType : "application/json; charset=utf-8",
			success : function (data){
				console.log(data);
				$("#res2").html(data);
			}, 
			error : function(jqXHR, textStatus, errorThrown){
	
			}
		});

		//3. object -> json
		$.ajax({
			async : true, //true(비동기)
			url : '<c:url value="/ajax/sample3"/>', 			
			type : 'get',										//직접 바로 확인하려고 get방식으로 
			data : obj,				//이거 없으면 () -> json (가능) //얘를 json객체로 만들어 보내주기 위해서
			dataType : "json",
			//contentType : "application/json; charset=utf-8",
			success : function (data){
				console.log(data);
				//$("#res3").html(JSON.stringify(data));				//객체를 문자열로 바꿔서 전송
				//여기서 person의 name만 가져오려면?				
				//$("#res3").html(data);	//중괄호: 객체 ->(컨트롤러에서 붙인 이름) person 객체 String객체 -> (personDTO의 필드명)name
				$("#res3").html(data.person.name + " : " + data.person.age);
				
			}, 
			error : function(jqXHR, textStatus, errorThrown){
	
			}
		});
		
		//4. 화면 받아오기
		/* object => String*/
		$.ajax({
			async : true, //true(비동기)
			url : '<c:url value="/ajax/sample4"/>', 			// 받아줄 애가 필요
			type : 'get', 
			data : {bo_num : 0}, 
			success : function (data){
				console.log(data);
				$("#res4").html(data);
			}, 
			error : function(jqXHR, textStatus, errorThrown){
	
			}
		});
		
		/////////////////////여기부턴 서버쪽처리//////////////////////////////////////////////
		
		
		// 5. object일때 fetch로 문자열 변경 (1번에서 가져온 정보로)
		fetch("<c:url value="/ajax/sample1?name=홍길동&age=20"/>")
		.then(res => res.text())
		.then(data=>{
			$("#res5").html("fetch : " + data);
		});
		
		// 6. 2번 처리
		fetch("<c:url value="/ajax/sample2"/>",{
			method : "post",									// 받는게 postmapping일때 여기 
			headers : { 'content-Type' : 'application/json'},
			body : JSON.stringify(obj)
		})															//뒤는 동일
		.then(res => res.text())
		.then(data=>{
			$("#res6").html("fetch : " + data);
		});
		
		// 7. 3번 처리  object json 처리방식이 다름
		fetch("<c:url value="/ajax/sample3?name=홍길동&age=20"/>")
		.then(res => res.json())
		.then(data=>{
			$("#res7").html("fetch : " + data.person.name);
		});
		
		
		// 8. 4번 처리  ajax로 했던걸 fetch로
		fetch("<c:url value="/ajax/sample4?bo_num=0"/>")
		.then(res => res.text())
		.then(data=>{
			$("#res8").html(data);
		});
		
		
		
		
		
		
		
		
	</script>

</body>
</html>
