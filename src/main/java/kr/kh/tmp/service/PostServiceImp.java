package kr.kh.tmp.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.kh.tmp.dao.PostDAO;
import kr.kh.tmp.model.vo.BoardVO;
import kr.kh.tmp.model.vo.FileVO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.model.vo.PostVO;
import kr.kh.tmp.utils.UploadFileUtils;

@Service
public class PostServiceImp implements PostService {

	@Autowired
	private PostDAO postDao;

	@Override
	public boolean insertBoard(String name) {

		try {
			return postDao.insertBoard(name);			//이미 발생한 예외에 대해서 catch하려고
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<BoardVO> getBoardList() {
		return postDao.selectBoardList();
	}

	@Override
	public boolean updateBoard(BoardVO board) {
		if(board == null) {
			return false;
		}
		
		try {
			return postDao.updateBoard(board);
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteBoard(int num) {
		return postDao.deleteBoard(num);
	}

	@Override
	public List<PostVO> getPostList(Integer bo_num) {
		return postDao.selectPostList(bo_num);
	}

	@Resource
	String uploadPath;

	@Override
	public boolean insertPost(PostVO post, MemberVO user, MultipartFile[] fileList) {

		if(user == null || post == null) {	//첨부파일은 null일수도 있기때문에 널체크 여기서 x
			return false;
		}
		
		post.setPo_me_id(user.getMe_id());
		
		boolean res = postDao.insertPost(post);	//여기 boolean으로 하는 이유는 추후 첨부파일 등록 하려고
		
		if(fileList == null || fileList.length == 0) return res;	
		
		for(MultipartFile file : fileList) uploadFile(file, post.getPo_num());

		return res;
	}
	
	private void uploadFile(MultipartFile file, int po_num) {//수정에서 사용하기 위해 메소드로 빼기
		String fi_ori_name = file.getOriginalFilename();
		if(fi_ori_name == null || fi_ori_name.length() == 00) return;		//이름 없으면 건너뜀
		
		try {
			String fi_name = UploadFileUtils.uploadFile(uploadPath, fi_ori_name, file.getBytes());
			FileVO fileVo = new FileVO(fi_ori_name, fi_name, po_num);
			postDao.insertFile(fileVo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void updateView(int po_num) {
		postDao.updateView(po_num);
	}

	@Override
	public PostVO getPost(int po_num) {
		return postDao.selectPost(po_num);
	}

	
	@Override
	public boolean deletePost(int po_num, MemberVO user) {

		if(user == null) {
			return false;
		}
		// 작성자 체크
		checkWriter(po_num, user);
		
		// 첨부파일(구현 시) 제거

		return postDao.deletePost(po_num);
		
		
	}
	private boolean checkWriter(int po_num, MemberVO user) {
		if(user == null) {
			return false;
		}
		
		PostVO post = postDao.selectPost(po_num);
		
		if(post == null) {
			return false;
		}
		
		return post.getPo_me_id().equals(user.getMe_id());
	}

	@Override
	public boolean updatePost(PostVO post, MemberVO user) {
		if(user==null || post==null) return false;
		//작성자 체크
		if(!checkWriter(post.getPo_num(), user)) {
			return false;
		}
		boolean res = postDao.updatePost(post);
	
		return res;
	}

	
}
