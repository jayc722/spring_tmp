package kr.kh.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kh.tmp.model.vo.CommentVO;
import kr.kh.tmp.pagination.CommentCriteria;
import kr.kh.tmp.pagination.Criteria;

public interface CommentDAO {

	boolean insertComment(@Param("comment")CommentVO comment);

	List<CommentVO> selectCommentList(@Param("criteria")Criteria cri);

	int selectTotalCommentCount(@Param("criteria")CommentCriteria cri);


}
