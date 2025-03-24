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

	@Override
	public MemberVO login(MemberVO member) {
		if(member==null) return null;
		//아이디 비번 유효성검사할필요 x -> 같은지 다른지만 비교하면 됨
		
		MemberVO user = memberDAO.selectMember(member.getMe_id());	//널체크 굳이 안하고 넘겨도 됨(어차피 아이디랑 일치 안해서 뱉어짐)
		
		if(user == null) {	//아이디가 다른 경우의 처리
			return null;
		}
		
		if(passwordEncoder.matches(member.getMe_pw(), user.getMe_pw())) {	//비밀번호가 다른 경우의 처리	->순서 바뀌면 안됨. 왼쪽이 암호화 안된 문자열 오른쪽이 암호화 된 문자열.
			return null;
		}
		
		return user;
	}
		
}

