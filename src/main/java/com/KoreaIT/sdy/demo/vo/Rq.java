package com.KoreaIT.sdy.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.sdy.demo.service.MemberService;
import com.KoreaIT.sdy.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		this.session = req.getSession();

		paramMap = Ut.getParamMap(req);

		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		this.req.setAttribute("rq", this);

	}

	public void printHistoryBackJs(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHitoryBack("F-B", msg));
	}

	public void printReplaceLoginJs(String msg, String afterLoginUri) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, "../member/login?afterLoginUri=" + afterLoginUri));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";

	}

	public String jsHistoryBack(String resultCode, String msg) {
		return Ut.jsHitoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	public String jsReplace(String uri) {
		return Ut.jsReplace(uri);
	}

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		System.out.println(currentUri);
		System.out.println(queryString);

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		System.out.println(currentUri);
		return currentUri;

	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	// Rq 객체 생성
	// 삭제 x
	public void run() {
		System.out.println("=======================run A ==========================");
	}

	public String getLoginUri() {
		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getLogoutUri() {
		String requestUri = req.getRequestURI();
		
		switch (requestUri) {
		case "/usr/article/write":
		case "/usr/article/modify":
			return "../member/doLogout?afterLogoutUri=" + getAfterLoginUri();
		}
		
		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}
	
	public String getJoinUri() {
		return "../member/join?afterLoginUri=" + getAfterLoginUri();
	}
	
	private String getAfterLoginUri() {
		return getEncodedCurrentUri();
	}
	
	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
	
	public String getArticleDetailUriFromArticleList(Article article) {
		
		return Ut.f("../article/detail?id=%d&boardId=%s", article.getId(), article.getBoardId());
	}
}
