package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ArticleService;
import com.KoreaIT.sdy.demo.service.BoardService;
import com.KoreaIT.sdy.demo.service.ReactionPointService;
import com.KoreaIT.sdy.demo.service.ReplyService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.Board;
import com.KoreaIT.sdy.demo.vo.Reply;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ArticleService articleService;

	@Autowired
	private Rq rq;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	ReactionPointService reactionPointService;
	
	@Autowired
	ReplyService replyService;

	// 액션 메서드
	@RequestMapping("/usr/article/detail")
	public String showDetail(int id, Model model) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), "article", id);
		
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
		
		
		if(actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		}
		
		model.addAttribute("article", article);
		model.addAttribute("replies", replies);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("isAlreadyAddGoodRp", reactionPointService.isAlreadyAddGoodRp(id, "article"));
		model.addAttribute("isAlreadyAddBadRp", reactionPointService.isAlreadyAddBadRp(id, "article"));
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData<?> doIncreaseHitCountRd(int id) {

		ResultData<?> increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData<?> rd =  ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));
		
		rd.setData2("id", id);
		
		return rd;
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1")int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1")int page)  {

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHistoryBackOnView("없는 게시판이야");
		}

		int articlesCount = articleService.articlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;

		int totalPage = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode, searchKeyword);
		
		List<Article> article_replies = articleService.getRepliesCount();
		
		for(Article article : articles) {
			for(Article article1: article_replies) {
				if(article.getId()==article1.getId()) {
					article.setRepliesCount(article1.getRepliesCount());
				}
			}
		}
		
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);
		model.addAttribute("article_replies", article_replies);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/write")
	public String write(String title, String body) {

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(String title, String body, int boardId, String replaceUri) {

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId(), boardId);

		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return Ut.jsReplace(writeArticleRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return Ut.jsHitoryBack("F-E", "게시글이 존재하지 않습니다.");
		}
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHitoryBack("F-1", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		articleService.deleteArticle(id);
		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list");
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시글은 존재하지 않습니다.", id));
		}

		ResultData<String> actorCanModifyRd = articleService.actorCanModifyRd(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body, String replaceUri) {

		Article article = articleService.getForPrintArticle(id);

		if (article == null) {
			return Ut.jsHitoryBack("F-E", Ut.f("%d번 게시글은 존재하지 않습니다.", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHitoryBack("F-1", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		ResultData<String> actorCanModifyRd = articleService.actorCanModifyRd(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return Ut.jsHitoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		article = articleService.getForPrintArticle(id);
		return articleService.modifyArticle(id, title, body, replaceUri);
	}

}