package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.KoreaIT.sdy.demo.service.ReplyService;
import com.KoreaIT.sdy.demo.service.ReactionPointService;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ReplyService replyService;

	@Autowired
	private Rq rq;

	@Autowired
	ReactionPointService reactionPointService;

//	// 액션 메서드
//	@RequestMapping("/usr/article/doWrite")
//	@ResponseBody
//	public String doWrite(int id, String body) {
//
//		if (Ut.empty(body)) {
//			return rq.jsHitoryBack("F-1", "내용을 입력해주세요.");
//		}
//
//	}
}