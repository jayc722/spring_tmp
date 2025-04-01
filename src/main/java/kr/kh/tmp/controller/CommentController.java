package kr.kh.tmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.kh.tmp.model.vo.CommentVO;
import kr.kh.tmp.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	
	@ResponseBody					//뷰리졸버 얘가 있어야 받아옴
	@PostMapping("/insert")
	//public boolean insert(@RequestBody int co_po_num, @RequestBody String co_content) {
	public boolean insert(@RequestBody CommentVO comment) {		//comment에 coponum cocontent 다 있어서
		System.out.println(comment);
		//화면에서 보낸 댓글 내용과 게시글 번호를 가져오는 작업...
		
		return false;
	}
}
