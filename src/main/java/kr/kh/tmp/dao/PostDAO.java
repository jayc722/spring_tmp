package kr.kh.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kh.tmp.model.vo.BoardVO;
import kr.kh.tmp.model.vo.FileVO;
import kr.kh.tmp.model.vo.LikeVO;
import kr.kh.tmp.model.vo.PostVO;
import kr.kh.tmp.pagination.Criteria;

public interface PostDAO {

	boolean insertBoard(@Param("bo_name")String name);

	List<BoardVO> selectBoardList();

	boolean updateBoard(@Param("board")BoardVO board);

	boolean deleteBoard(@Param("bo_num")int num);

	List<PostVO> selectPostList(@Param("criteria")Criteria cri);

	boolean insertPost(@Param("post")PostVO post);

	void updateView(@Param("po_num")int po_num);

	PostVO selectPost(@Param("po_num")int po_num);

	boolean deletePost(@Param("po_num")int po_num);

	boolean updatePost(@Param("post")PostVO post);

	void insertFile(@Param("file")FileVO fileVo);	//param은 xml에 사용할 매퍼 이름과 맞춰주면 됨

	List<FileVO> selectFileList(@Param("po_num")int po_num);

	void deleteFile(@Param("fi_num")int fi_num);

	FileVO selectFile(@Param("fi_num")int fi_num);

	int selectPostCount(@Param("criteria")Criteria cri);
	
	//LikeVO selectLike(@Param("like")LikeVO like);

	LikeVO selectLike(@Param("li_po_num")int li_po_num, @Param("li_me_id")String li_me_id);

	//void insertLike(@Param("like")LikeVO like);

	//void updateLike(@Param("like")LikeVO like);

	void insertLike(@Param("li_po_num")int li_po_num, @Param("li_state")int li_state, @Param("li_me_id")String li_me_id);
	
	void updateLike(@Param("li_num")int li_num, @Param("li_state")int li_state);

	void updatePostLike(@Param("li_po_num")int li_po_num);
	

	
	
}
