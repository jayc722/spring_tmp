package kr.kh.tmp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.kh.tmp.model.vo.MemberVO;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public void postHandle(
	    HttpServletRequest request, 
	    HttpServletResponse response, 
	    Object handler, 
	    ModelAndView mv)
	    throws Exception {
	
		// 넘겨준 회원 정보를 가져옴
		MemberVO user = (MemberVO)mv.getModel().get("user");				// 여기 "유저" 를 홈컨트롤러 이름이랑 맞춰야
		// 회원 정보가 있으면 ( = 로그인 성공시)
		if(user != null) {
			// 세션에 회원 정보 추가
			request.getSession().setAttribute("user", user);				//얘는 위랑 이름 맞출 필요 없지만 그냥 알아보기 쉽게 하려고
			
		}
		
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
			
			//구현
			return true;
	}
}