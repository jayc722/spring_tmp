package kr.kh.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kh.tmp.model.vo.CommentVO;
import kr.kh.tmp.pagination.CommentCriteria;
import kr.kh.tmp.pagination.Criteria;

public interface CommentDAO {

	boolean insertComment(@Param("comment")CommentVO comment);

	List<CommentVO> selectCommentList(@Param("criteria")Criteria cri);				//commentCriteria criteria로 수정 안해도 되긴하는데 다형성 때문에 일단 수정...

	int selectTotalCommentCount(@Param("criteria")Criteria cri);


}
