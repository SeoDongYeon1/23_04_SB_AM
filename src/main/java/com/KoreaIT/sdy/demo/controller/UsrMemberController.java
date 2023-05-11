package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.MemberService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.Member;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);

		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req, String loginId, String loginPw) {

		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}

		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return rq.jsHistoryBack("F-3", "아이디 또는 비밀번호를 확인해주세요.");
		}

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("F-4", "아이디 또는 비밀번호를 확인해주세요.");
		}

		rq.login(member);

		// 우리가 갈 수 있는 경로를 경우의 수로 표현, 인코딩, 그 외에는 처리 불가 -> 메인으로 보낸다.
		return Ut.jsReplace(Ut.f("%s님 로그인 되었습니다.", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();
		return Ut.jsReplace("로그아웃되었습니다.", afterLogoutUri);
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
			return rq.jsHistoryBack("F-E", "존재하지 않는 회원입니다.");
		}

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}

		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-1", "이름을 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-2", "닉네임을 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-3", "전화번호를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-4", "이메일을 입력해주세요.");
		}

		ResultData modifyRd = memberService.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);

		return rq.jsReplace(modifyRd.getMsg(), "../member/profile");
	}

	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요.");
		}
		
		Member existsMember = memberService.getMemberByLoginId(loginId);
		
		if(existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중인 아이디입니다.", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능한 아이디입니다.", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/findLoginId")
	public String showLoginId() {
		
		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name, String email) {
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if(member==null) {
			return Ut.jsHitoryBack("F-1", "입력된 정보로 가입된 아이디가 없습니다.");
		}
		
		return Ut.jsReplace("S-1", Ut.f("아이디 : %s", member.getLoginId()), afterFindLoginIdUri);
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String showLoginPw() {
		
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(@RequestParam(defaultValue = "/") String afterFindLoginPwUri, String loginId, String email) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member==null) {
			return Ut.jsHitoryBack("F-1", "입력된 정보로 가입된 아이디가 없습니다.");
		}
		
		if(member.getEmail().equals(email)==false) {
			return Ut.jsHitoryBack("F-2", "일치하는 이메일이 없습니다.");
		}
		
		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmail(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getResultCode(), notifyTempLoginPwByEmailRd.getMsg(),
				afterFindLoginPwUri);
	}
}