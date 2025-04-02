package kr.kh.tmp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kh.tmp.dao.CommentDAO;
import kr.kh.tmp.model.vo.CommentVO;
import kr.kh.tmp.model.vo.MemberVO;
import kr.kh.tmp.pagination.CommentCriteria;
import kr.kh.tmp.pagination.Criteria;
import kr.kh.tmp.pagination.PageMaker;

@Service
public class CommentServiceImp implements CommentService{

	@Autowired
	CommentDAO commentDao;
	
	@Override
	public boolean insertComment(CommentVO comment, MemberVO user) {
		// TODO Auto-generated method stub
		if(comment == null /*|| user == null*/) return false;
		
		//comment.setCo_me_id(user.getMe_id());
		comment.setCo_me_id("123"); //로그인 없이 테스트하려고 우선
		
		return commentDao.insertComment(comment);
	}

	@Override
	public List<CommentVO> getCommentList(Criteria cri) {	//여기 cri는 항상 호출할때 기본생성자로 만들어서 오지만 언제나 널체크 하는 습관 들이기
		if(cri==null)return null;

		
		
		return commentDao.selectCommentList(cri);			//메소드 이름까지 맞출 필요는 없지만 적어도 같은기능 메소드는 통일하는게 좋음
	}

	@Override
	public PageMaker getPageMaker(CommentCriteria cri) {
		if(cri==null)return null;
		int totalCount = commentDao.selectTotalCommentCount(cri);
		PageMaker pm = new PageMaker(10, cri, totalCount);
		return pm;
	}

}
