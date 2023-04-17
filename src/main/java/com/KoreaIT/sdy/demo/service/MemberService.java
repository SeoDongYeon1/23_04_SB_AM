package com.KoreaIT.sdy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	// 생성자
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// 서비스 메서드
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
	}

}
