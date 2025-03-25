package kr.kh.tmp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kh.tmp.dao.PostDAO;

@Service
public class PostServiceImp implements PostService {

	@Autowired
	private PostDAO postDao;

	@Override
	public boolean insertBoard(String name) {

		try {
			return postDao.insertBoard(name);			//이미 발생한 예외에 대해서 catch하려고
		}catch (Exception e) {
			return false;
		}
	}
	
	
	
	
}
