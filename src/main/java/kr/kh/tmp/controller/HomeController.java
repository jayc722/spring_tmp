package kr.kh.tmp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.kh.tmp.model.dto.PersonDTO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.service.MemberService;



@Controller
public class HomeController {
	
	
	@Autowired
	private MemberService memberService;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@GetMapping("/signup")
	public String signup(Model model, String id) {
		model.addAttribute("id", id);
		return "/member/signup";
	}
	
	@PostMapping("/signup")
	public String signupPost(Model model, MemberVO member) {
		if(memberService.signup(member)) {
			model.addAttribute("url","/");
			model.addAttribute("msg","회원 가입에 성공했습니다.");
		}else {			//아이디 중복검사 넣으면 회원가입 실패 자체는 없어야 하지만 아직은 구현 안했기 때문에
			model.addAttribute("url","/signup?id=" + member.getMe_id());	// 입력한 아이디 그대로 넣어줌
			model.addAttribute("msg","회원 가입에 실패했습니다.");
		}
		//return "/member/signup";
		return "message"; //두번째 방식 사용
	}

	@GetMapping("/login")
	public String login(Model model, String id) {
		model.addAttribute("id", id);
		return "/member/login";
	}
	
	@PostMapping("/login")
	public String loginPost(Model model, MemberVO member) {
		
		MemberVO user = memberService.login(member);
		if(user != null) {
			model.addAttribute("url","/");
			model.addAttribute("msg","로그인에 성공했습니다.");
			model.addAttribute("user", user);	//여기 "유저" 명이랑 interceptor "유저"랑 일치시켜야 받을수있음.
		}else {			
			model.addAttribute("url","/login?id=" + member.getMe_id());	
			model.addAttribute("msg","로그인에 실패했습니다.");
		}

		//System.out.println(user);

		return "message"; //두번째 방식 사용
	}

	
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {		//->로그아웃은 간단하게 세션의 회원정보 지우는 것만으로 끝
		
		session.removeAttribute("user");			// 로그인인터셉터에서 post매퍼랑 안 맞춰도 된다고 했던 "user"랑 이름 맞추면 됨
		
		model.addAttribute("url", "/");		//로그아웃 성공시 url을 메인으로
		model.addAttribute("msg", "로그아웃 했습니다."); 
		
		return "message";
	}
	
	@ResponseBody // 뷰 리졸버가 분석하지 않고 그대로 서버로 보내는 역할
	@PostMapping("/check/id")
	//리턴타입 꼭 Object일 필요는 없음. List로 보내고 싶으면 List로 수정해도 상관없음 
	public boolean checkId(@RequestParam("id") String id){
		return memberService.checkId(id);
	}
	
	
	@ResponseBody
	@GetMapping("/ajax/sample1")
	public Object ajaxSample1(@RequestParam String name, @RequestParam int age) { //requestParam으로 순서대로 받아옴
		System.out.println(name + " : " + age);
		return "home1";		//콘솔창에 home.jsp 코드가 그대로 나옴.
		// 리턴값이 문자열이면 뷰 리졸버가 얘를 가지고 분석하는데 타일즈뷰리졸버가  <definition name="*" extends=".root"> 로 잡아와서 콘솔창에 띄우는것
		// home이라는 문자열로 보내고 싶다면 -> responsebody 추가(home 문자열로 보냄)
		// http://localhost:8080/tmp/ajax/sample1?name=홍길동&age=21 와 같음
	}
	
	@ResponseBody
	@PostMapping("/ajax/sample2")
	public Object ajaxSample2(@RequestBody PersonDTO person) { 
		System.out.println(person);			//변수가 많으면 객체로 묶어서 request body로 묶어보내는게 편함...
		return "home2";	 
	}
	
	@ResponseBody
	@GetMapping("/ajax/sample3")
	public Map<String, Object> ajaxSample3(@RequestParam String name, @RequestParam int age) { //requestParam으로 순서대로 받아옴
		PersonDTO person = new PersonDTO();
		person.setName(name);
		person.setAge(age);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("person", person);	//"화면에서 쓸 이름" , 데이터
		map.put("string", "안녕하세요.");
		
		return map;		//
	}
	
	@ResponseBody
	@GetMapping("/ajax/sample4")
	public Object ajaxSample4(@RequestParam int bo_num) { //requestParam으로 순서대로 받아옴
		return "redirect:/post/list?bo_num=" + bo_num;	// 이대로 하면 이 문자열 그대로만 나옴 	
	}
	
}
