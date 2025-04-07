package kr.kh.tmp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.kh.tmp.dao.MemberDAO;
import kr.kh.tmp.model.vo.BoardVO;
import kr.kh.tmp.model.vo.FileVO;
import kr.kh.tmp.model.vo.LikeVO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.model.vo.PostVO;
import kr.kh.tmp.pagination.PageMaker;
import kr.kh.tmp.pagination.PostCriteria;
import kr.kh.tmp.service.MemberService;
import kr.kh.tmp.service.PostService;
import kr.kh.tmp.service.PostServiceImp;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MemberService memberService;
	

	
	@GetMapping("/list")
	public String list(Model model, PostCriteria cri) {
		cri.setPerPageNum(2);//페이지당 게시글수 2개로 변경(criteria에 직접해도 되긴하는데)
		List<PostVO> list = postService.getPostList(cri);
		
		List<BoardVO> boardList = postService.getBoardList();
		
		PageMaker pm = postService.getPageMaker(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageMaker", pm);
		return "/post/list";
	}
	
	@GetMapping("/insert")
	public String insert(Model model, Integer bo_num) {
		bo_num = bo_num == null ? 0 : bo_num;
		
		List<BoardVO> boardList = postService.getBoardList();
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("bo_num", bo_num);
		return "/post/insert";
	}
	
	@PostMapping("/insert")
	public String insertPost(Model model, PostVO post, HttpSession session, MultipartFile[] fileList) {	//여기 multipartfile 변수 이름은 jsp의 첨부파일 name속성이랑 맞ㅛ
		//System.out.println(post);
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(postService.insertPost(post,user, fileList)) {
			model.addAttribute("url", "/post/list");
			model.addAttribute("msg", "게시글을 등록했습니다.");
		}else {
			model.addAttribute("url", "/post/list");
			model.addAttribute("msg", "게시글을 등록하지 못했습니다.");
	
		}
		return "message";
	}
	
	@GetMapping("detail/{po_num}")
	public String detail(Model model, @PathVariable("po_num")int po_num) {	//url에서 슬래시 사이에 있는 값을 받아오는 어노테이션 ?로 받아오거나 이거 쓰거나
		//조회수 증가
		postService.updateView(po_num);
		//게시글을 가져와서 화면에 전달
		PostVO post = postService.getPost(po_num);
		List<FileVO> fileList = postService.getFileList(po_num);
		
		
		model.addAttribute("post", post);
		model.addAttribute("file", fileList);
		
		return "/post/detail";
	}

	
	@GetMapping("/delete/{po_num}")
	public String delete(Model model, @PathVariable("po_num")int po_num, HttpSession session) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(postService.deletePost(po_num, user)) {
			model.addAttribute("url", "/post/list");
			model.addAttribute("msg", "게시글을 삭제했습니다.");
		}else {
			model.addAttribute("url", "/post/detail/"+po_num);
			model.addAttribute("msg", "게시글을 삭제하지 못했습니다.");
		}
		
		return "message";
	}
	
	@GetMapping("/update/{po_num}")
	public String update(Model model, @PathVariable("po_num")int po_num, HttpSession session) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		PostVO post = postService.getPost(po_num);
		//로그인 안했거나 없는 게시글이거나 작성자가 아니면
		if(post == null || user == null || !post.getPo_me_id().equals(user.getMe_id())) {
			model.addAttribute("url", "/post/detail/"+po_num);
			model.addAttribute("msg", "작성자가 아니거나 없는 게시글입니다.");
			return "message";
		}
		
		List<BoardVO> board = postService.getBoardList();
		List<FileVO> fileList = postService.getFileList(po_num); 
		
		
		model.addAttribute("post", post);
		model.addAttribute("board", board);
		model.addAttribute("file", fileList);
		return "/post/update";
	}
	

	
	@PostMapping("/update")
	public String updatePost(Model model, PostVO post, HttpSession session, MultipartFile[] fileList, int [] delNums) {
		
		//for(MultipartFile file : fileList)System.out.println(file.getOriginalFilename());
		//for(int num : delNums)System.out.println(num);
		//항상 체크하는 버릇!
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(postService.updatePost(post, user, fileList, delNums)) {
			model.addAttribute("msg", "게시글을 수정했습니다.");
		}else {
			model.addAttribute("msg", "게시글을 수정하지 못했습니다.");
		}
		
		model.addAttribute("url", "/post/detail/" + post.getPo_num());
		return "message";
	}
	
	@ResponseBody
	@PostMapping("/like")
	public int like(Model model, @RequestBody LikeVO like, HttpSession session) {
		
		//System.out.println(like);
		MemberVO user = (MemberVO)session.getAttribute("user");
		/*
		//System.out.println(user);
		if(like==null||user==null)return 2;
		
		MemberVO member = memberService.selectMember(user.getMe_id());
		
		if(member==null) return 2;
		
		like.setLi_me_id(member.getMe_id());
		*/
		return postService.updateLike(like, user);
	}
	
	
}
