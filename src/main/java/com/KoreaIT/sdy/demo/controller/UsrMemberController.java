package com.KoreaIT.sdy.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.MemberService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Member;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

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
			return joinRd;
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, "member", member);
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req, String loginId, String loginPw) {
		if(loginId!=null && loginId!="") {
			return Ut.f("""
					<script>
					const loginId = '%s';
					</script>
					""", loginId);
		}
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		if (rq.isLogined()) {
			return Ut.jsHistroyBack("F-A", "이미 로그인 상태입니다.");
		}

		if (Ut.empty(loginId)) {
			return Ut.jsHistroyBack("F-1", "아이디를 입력해주세요");
		}
		
		if (Ut.empty(loginPw)) {
			return Ut.jsHistroyBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistroyBack("F-3", "아이디 또는 비밀번호를 확인해주세요.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistroyBack("F-4", "아이디 또는 비밀번호를 확인해주세요.");
		}
		
		rq.login(member);
		return Ut.jsReplace(Ut.f("%s님 로그인 되었습니다.", member.getNickname()), "../article/list");
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {
		Rq rq = (Rq) req.getAttribute("rq");

		if (rq.isLogined() == false) {
			return Ut.jsHistroyBack("F-A", "로그인 후 이용해주세요.");
		}
		
		rq.logout();
		return Ut.jsReplace("로그아웃되었습니다.", "../home/main");
	}
}