package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ArticleService;
import com.KoreaIT.sdy.demo.service.BoardService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.Board;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	
	// 액션 메서드
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, int id, Model model) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model, int boardId) {
		Board board = boardService.getBoardById(boardId);
		
		if(board==null) {
			return Ut.jsHistroyBackOnView(req, "존재하지 않는 게시판입니다.");
		}
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		List<Article> articles = articleService.getForPrintArticles(boardId);
		
		model.addAttribute("articles", articles);
		model.addAttribute("board", board);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, String title, String body) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(Ut.empty(title)) {
			return Ut.jsHistroyBack("F-1", "제목을 입력해주세요.");
		}
		if(Ut.empty(body)) {
			return Ut.jsHistroyBack("F-2", "내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId());
		
		int id = (int) writeArticleRd.getData1();
		
		if(Ut.empty(replaceUri)) {
			replaceUri=Ut.f("../article/detail?id=%d", id);
		}
		
		return Ut.jsReplace(writeArticleRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if (article==null) {
			return Ut.jsHistroyBack("F-E", "게시글이 존재하지 않습니다.");
		}
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistroyBack("F-1", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		
		articleService.deleteArticle(id);
		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list");
		//return Ut.f("<script>alert('%d번 글이 삭제되었습니다.'); location.replace('list')</script>", id); 
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if (article==null) {
			return Ut.jsHistroyBackOnView(req, Ut.f("%d번 게시글은 존재하지 않습니다.", id));
		}
		
		ResultData<String> actorCanModifyRd = articleService.actorCanModifyRd(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return Ut.jsHistroyBackOnView(req, actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(id);
		
		if(article==null) {
			return Ut.jsHistroyBack("F-E", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistroyBack("F-1", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		
		ResultData<String> actorCanModifyRd = articleService.actorCanModifyRd(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return Ut.jsHistroyBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}
		
		article = articleService.getForPrintArticle(id);
		return articleService.modifyArticle(id, title, body); 
	}

}