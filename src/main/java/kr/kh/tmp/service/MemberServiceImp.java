package kr.kh.tmp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kh.tmp.dao.MemberDAO;
import kr.kh.tmp.model.vo.MemberVO;

@Service
public class MemberServiceImp implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Override
	public boolean signup(MemberVO member) {
		if(member==null) {
			return false;
		}
		//아이디 비번 유효성검사(했다치고)
		
		
		return memberDAO.insertMember(member);
	}
}

