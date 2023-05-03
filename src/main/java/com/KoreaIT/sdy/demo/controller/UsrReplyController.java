package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ReplyService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Reply;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ReplyService replyService;

	@Autowired
	private Rq rq;

	// 액션 메서드
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.empty(body)) {
			return rq.jsHitoryBack("F-2", "내용을 입력해주세요.");
		}

		ResultData writeReplyRd = replyService.writeReply(relTypeCode, relId, body, rq.getLoginedMemberId());

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}

		return Ut.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(String relTypeCode, int relId, String body, String replaceUri) {
		
		if (Ut.empty(body)) {
			return rq.jsHitoryBack("F-2", "내용을 입력해주세요.");
		}
		
		ResultData writeReplyRd = replyService.writeReply(relTypeCode, relId, body, rq.getLoginedMemberId());
		
		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}
		
		return Ut.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {
		
		Reply reply = replyService.getReply(id);
		
		if (reply == null) {
			return Ut.jsHitoryBack("F-1", Ut.f("%d번 댓글이 존재하지 않습니다.", id));
		}
		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHitoryBack("F-1", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}
		
		ResultData deleteReplyRd = replyService.deleteReply(id);
		
		if(Ut.empty(replaceUri)) {
			switch(reply.getRelTypecode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return Ut.jsReplace(Ut.f("%d번 댓글을 삭제 했습니다", id), replaceUri);
	}
}