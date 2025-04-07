package kr.kh.tmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.kh.tmp.dao.PostDAO;
import kr.kh.tmp.model.vo.BoardVO;
import kr.kh.tmp.model.vo.FileVO;
import kr.kh.tmp.model.vo.LikeVO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.model.vo.PostVO;
import kr.kh.tmp.pagination.Criteria;
import kr.kh.tmp.pagination.PageMaker;
import kr.kh.tmp.pagination.PostCriteria;
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
		if(!checkWriter(po_num, user)) return false;
		
		List<FileVO> files = postDao.selectFileList(po_num); //제거가 실제로 지우는게 아니기 때문에 첨부파일 제거를 제거 뒤에 해도 됨
		
		if(!postDao.deletePost(po_num)) return false;	//제거 실행 후 실패시 반환
		
		// 첨부파일(구현 시) 제거
		//System.out.println(files);
		if(files.isEmpty() || files == null) return true;
		
		for(FileVO file : files) {
			if(file == null) continue;
			//System.out.println(file);
			UploadFileUtils.deleteFile(uploadPath, file.getFi_name());
			postDao.deleteFile(file.getFi_num());
		}
		
		return true;

		
		
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
	public boolean updatePost(PostVO post, MemberVO user, MultipartFile[] fileList, int[] delNums) {
		if(user==null || post==null) return false;
		//작성자 체크
		if(!checkWriter(post.getPo_num(), user)) {
			return false;
		}
		boolean res = postDao.updatePost(post);
	
		if(!res || fileList == null) return res;
		
		for(MultipartFile file : fileList) uploadFile(file,post.getPo_num());
		
		//삭제할 첨부파일 꺼내서 제거
		if(delNums == null || delNums.length == 0) return res;
		
		for(int fi_num : delNums) {
			FileVO fileVO = postDao.selectFile(fi_num);
			if(post.getPo_num() != fileVO.getFi_po_num()) continue;	//화면에서 보낸건 개발자도구로 얼마든지 변조 가능하니 항상 체크해야 함. 
			//deleteFile(fileVO);
			if(fileVO == null) continue;
			UploadFileUtils.deleteFile(uploadPath, fileVO.getFi_name());
			postDao.deleteFile(fileVO.getFi_num());
		}
		return res;
	}

	@Override
	public List<FileVO> getFileList(int po_num) {
		
		return postDao.selectFileList(po_num);
	}

	@Override
	public List<PostVO> getPostList(Criteria cri) {	//bo_num 에서 수정

		return postDao.selectPostList(cri);
	}

	@Override
	public PageMaker getPageMaker(Criteria cri) {
		if(cri==null)return null;
		
		int totalCount=postDao.selectPostCount(cri);
		return new PageMaker(3, cri, totalCount);
	}

	/*
	@Override
	public int selectLike(LikeVO like) {
		
		LikeVO dbLike = postDao.selectLike(like);
		if(dbLike == null) return 2;
		
		return dbLike.getLi_state();
	}*/

	@Override
	public int updateLike(LikeVO like, MemberVO user) {
		if(like == null) return -2; //오류 발생 시 -2 리턴
		
		LikeVO dbLike = postDao.selectLike(like.getLi_po_num(), user.getMe_id());
		//LikeVO dbLike = postDao.selectLike(like.getLi_po_num(), "123");
		
		if(dbLike==null) {
			
			postDao.insertLike(like.getLi_po_num(), like.getLi_state(), user.getMe_id());
			//postDao.insertLike(like.getLi_po_num(),like.getLi_state(), "123");
			
			postDao.updatePostLike(like.getLi_po_num());
			return like.getLi_state();
		}
		
		if(like.getLi_state() == dbLike.getLi_state()) {
			like.setLi_state(0);
			//postDao.updateLike(like.getLi_po_num(), 0,  user.getMe_id());
			//postDao.updateLike(like.getLi_po_num(), 0, "123");
		}
		
		
			postDao.updateLike(dbLike.getLi_num(), like.getLi_state());
			//postDao.updateLike(like.getLi_po_num(), like.getLi_state(),  "123");


		postDao.updatePostLike(like.getLi_po_num());
		return like.getLi_state();
	}

	@Override
	public void updatePostLike(int po_num) {
		postDao.updatePostLike(po_num);
		
	}
	
	

	
}
