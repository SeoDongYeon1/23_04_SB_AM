package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ArticleService;
import com.KoreaIT.sdy.demo.service.ReplyService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.Reply;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired 
	private ReplyService replyService;
	
	@Autowired 
	private ArticleService articleService;

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
	
	@RequestMapping("/usr/reply/modify")
	public String showModify(Model model, int id, String replaceUri) {

		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBackOnView(actorCanModifyRd.getMsg());
		}

		Article article = articleService.getArticleById(reply.getRelId());

		model.addAttribute("reply", reply);
		model.addAttribute("article", article);

		return "usr/reply/modify";
	}

//	@RequestMapping("/usr/article/doModify")
//	@ResponseBody
//	public String doModify(int id, String title, String body) {
//
//		Article article = articleService.getArticle(id);
//
//		if (article == null) {
//			return rq.jsHitoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
//		}
//
//		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
//
//		if (actorCanModifyRd.isFail()) {
//			return rq.jsHitoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
//		}
//
//		articleService.modifyArticle(id, title, body);
//
//		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
//	}

}