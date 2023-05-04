package com.KoreaIT.sdy.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Autowired
	private Rq rq;

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
		
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {
		
		if (Ut.empty(loginId)) {
			return rq.jsHitoryBack("F-1", "아이디를 입력해주세요");
		}
		
		if (Ut.empty(loginPw)) {
			return rq.jsHitoryBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return rq.jsHitoryBack("F-3", "아이디 또는 비밀번호를 확인해주세요.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return rq.jsHitoryBack("F-4", "아이디 또는 비밀번호를 확인해주세요.");
		}
		
		rq.login(member);
		return Ut.jsReplace(Ut.f("%s님 로그인 되었습니다.", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout() {
		
		rq.logout();
		return Ut.jsReplace("로그아웃되었습니다.", "../home/main");
	}
	
	@RequestMapping("/usr/member/profile")
	public String showProfile(Model model) {
		Member member = rq.getLoginedMember();
		
		
		model.addAttribute("member", member);
		return "usr/member/profile";
	}
	
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw(Model model) {
		Member member = rq.getLoginedMember();
		
		model.addAttribute("member", member);
		
		return "usr/member/checkPw";
	}
	
//	@RequestMapping("/usr/member/doCheckPw")
//	@ResponseBody
//	public String doCheckPw(String loginPw) {
//		
//		if (Ut.empty(loginPw)) {
//			return rq.jsHitoryBack("F-1", "비밀번호를 입력해주세요");
//		}
//		
//		Member member = rq.getLoginedMember();
//
//		if (member.getLoginPw().equals(loginPw) == false) {
//			return rq.jsHitoryBack("F-2", "비밀번호가 틀렸습니다.");
//		}
//		
//		return rq.jsReplace("../member/modify");
//	}
	
	@RequestMapping("/usr/member/modify")
	public String modify(Model model) {
		
		Member member = rq.getLoginedMember();
		
		model.addAttribute("member", member);
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		Member member = memberService.getMemberById(id);
		
		if (member == null) {
			return rq.jsHitoryBack("F-E", "존재하지 않는 회원입니다.");
		}
		
		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(name)) {
			return rq.jsHitoryBack("F-1", "이름을 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHitoryBack("F-2", "닉네임을 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHitoryBack("F-3", "전화번호를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return rq.jsHitoryBack("F-4", "이메일을 입력해주세요.");
		}
		
		ResultData modifyRd = memberService.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		return rq.jsReplace(modifyRd.getMsg(), "../member/profile");

	}
}