package kr.kh.tmp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kh.tmp.dao.MemberDAO;
import kr.kh.tmp.model.vo.MemberVO;

@Service
public class MemberServiceImp implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public boolean signup(MemberVO member) {
		if(member==null) {
			return false;
		}
		//아이디 비번 유효성검사(했다치고)
		
		
		//비번 암호화
		String encPw = passwordEncoder.encode(member.getMe_pw());
		member.setMe_pw(encPw);
		
		try {
			
			return memberDAO.insertMember(member);
			
		}catch(Exception e){	//가입된 아이디로 가입한 경우
			e.printStackTrace();		// 에러문구 뜨는게 거술리면 주석처리
			return false;
		}
		
	}
}

