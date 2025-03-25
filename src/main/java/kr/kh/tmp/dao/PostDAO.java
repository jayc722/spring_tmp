package kr.kh.tmp.dao;

import org.apache.ibatis.annotations.Param;

import kr.kh.tmp.model.vo.MemberVO;

public interface PostDAO {

	boolean insertBoard(@Param("bo_name")String name);

	
	
}
