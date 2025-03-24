package kr.kh.tmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		//return "/member/signup";
		return "message"; //두번째 방식 사용
	}

}
