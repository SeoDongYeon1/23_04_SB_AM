package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.MemberService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Member;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Controller
public class UsrMemberController {
		@Autowired
		private MemberService memberService;

		@RequestMapping("/usr/member/doJoin")
		@ResponseBody
		public ResultData<?> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
				String email) {

			if (Ut.empty(loginId)) {
				return ResultData.from("F-1", "아이디를 입력해주세요");
			}
			if (Ut.empty(loginPw)) {
				return ResultData.from("F-2", "비밀번호를 입력해주세요");
			}
			if (Ut.empty(name)) {
				return ResultData.from("F-3", "이름을 입력해주세요");
			}
			if (Ut.empty(nickname)) {
				return ResultData.from("F-4", "닉네임을 입력해주세요");
			}
			if (Ut.empty(cellphoneNum)) {
				return ResultData.from("F-5", "전화번호를 입력해주세요");
			}
			if (Ut.empty(email)) {
				return ResultData.from("F-6", "이메일을 입력해주세요");
			}

			ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

			if (joinRd.isFail()) {
				return (ResultData<Integer>) joinRd;
			}

			Member member = memberService.getMemberById(joinRd.getData1());

			return ResultData.newData(joinRd, member);
		}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public Object doLogin(String loginId, String loginPw) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member==null) {
			return "아이디 또는 비밀번호를 확인해주세요.";
		}
		
		if(member.getLoginPw().equals(loginPw)==false) {
			return "아이디 또는 비밀번호를 확인해주세요.";
		}
		return member.getNickname()+ "님 로그인 되었습니다.";
	}
}