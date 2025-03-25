package kr.kh.tmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kh.tmp.service.PostService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private PostService postService; 
	
	@GetMapping("/board")	// admin을 제외한 뒷부분 써주기
	public String board() {
		return "/post/board";
	}
	
	@PostMapping("/board/insert")	
	public String boardPost(Model model, String name) {		//메세지jsp에 메세지 주고받으려면 model 필요
		
		if(postService.insertBoard(name)) {
			model.addAttribute("msg", "게시판을 등록했습니다.");
		}else {
			model.addAttribute("msg", "이미 등록된 게시판입니다.");
		}
		model.addAttribute("url", "/admin/board");
		
		return "message";
	}

}
