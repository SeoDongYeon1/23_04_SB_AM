package com.KoreaIT.sdy.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.MemberRepository;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Member;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		// 로그인 아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("(%s)는 이미 사용중인 아이디입니다", loginId));
		}
		
		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);
		
		if(existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		loginPw = Ut.sha256(loginPw);
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	public Member getMemberByLoginId(String loginId) {
		Member member = memberRepository.getMemberByLoginId(loginId);
		
		return member;
	}
	
	public Member getMemberById(int id) {
		Member member = memberRepository.getMemberById(id);
		
		return member;
	}

	public ResultData modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		return ResultData.from("S-1", "회원 정보 수정이 완료되었습니다");
	}


}
