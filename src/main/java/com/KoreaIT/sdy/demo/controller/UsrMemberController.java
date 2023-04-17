package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.MemberService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Member;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	// 액션 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(Ut.empty(loginId)) {
			return "아이디를 입력해주세요.";
		}
		if(Ut.empty(loginPw)) {
			return "비밀번호를 입력해주세요.";
		}
		if(Ut.empty(name)) {
			return "이름을 입력해주세요.";
		}
		if(Ut.empty(nickname)) {
			return "닉네임을 입력해주세요.";
		}
		if(Ut.empty(cellphoneNum)) {
			return "휴대폰 번호를 입력해주세요.";
		}
		if(Ut.empty(email)) {
			return "이메일을 입력해주세요.";
		}
		
		Member member = memberService.getMemberByloginId(loginId);
		
		if(member!=null) {
			return "이미 사용중인 아이디입니다.";
		}
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		member = memberService.getMemberById(id);
		return member;
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public Object doLogin(String loginId, String loginPw) {
		Member member = memberService.getMemberByloginId(loginId);
		
		if(member==null) {
			return "아이디 또는 비밀번호를 확인해주세요.";
		}
		
		if(member.getLoginPw().equals(loginPw)==false) {
			return "아이디 또는 비밀번호를 확인해주세요.";
		}
		return member.getNickname()+ "님 로그인 되었습니다.";
	}

}