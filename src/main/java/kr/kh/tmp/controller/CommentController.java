package kr.kh.tmp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.kh.tmp.model.vo.CommentVO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.pagination.CommentCriteria;
import kr.kh.tmp.pagination.PageMaker;
import kr.kh.tmp.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	
	@ResponseBody					//뷰리졸버 얘가 있어야 받아옴
	@PostMapping("/insert")
	//public boolean insert(@RequestBody int co_po_num, @RequestBody String co_content) {
	public boolean insert(@RequestBody CommentVO comment, HttpSession session) {		//comment에 coponum cocontent 다 있어서
		//System.out.println(comment);
		//화면에서 보낸 댓글 내용과 게시글 번호를 가져오는 작업...
		
		MemberVO user = (MemberVO)session.getAttribute("user");			//회원 정보를 가져오고 게시글 등록에 활용
		
		return commentService.insertComment(comment,user);
	}
	
	
	@ResponseBody	//json으로 보냈으니 body로 받아야
	@PostMapping("/list")
	//public String list() {
	//public String list(@RequestBody Criteria cri) {	//이렇게 하면 페이지는 받아와지는데 게시글 번호(po_num)가 안받아와짐 because criteria에는 po_num이 없고 postCriteria는 bo_num으로 되어있기때문...
	public String list(@RequestBody CommentCriteria cri) {	//CommentCriteria 클래스 새로 생성해서(po_num을 포함하는) 객체로 만들면 받아와짐
		//System.out.println(cri);
		List<CommentVO> commentList =  commentService.getCommentList(cri);
		
		if(commentList.isEmpty())return null;
		for(CommentVO comment : commentList) {
			System.out.println(comment);
		}
		
		PageMaker pm =  commentService.getPageMaker(cri);
		System.out.println(pm);
		
		
		return "";
	}
	
	
}
