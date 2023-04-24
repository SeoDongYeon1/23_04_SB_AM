package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ArticleService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ArticleService articleService;
	
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
	public String showList(Model model) {
		List<Article> articles = articleService.getForPrintArticles();
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, String title, String body) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(Ut.empty(title)) {
			return "<script>alert('제목을 입력해주세요.'); location.replace('list')</script>";
		}
		if(Ut.empty(body)) {
			return "<script>alert('내용을 입력해주세요.'); location.replace('list')</script>";
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId());
		
		int id = (int) writeArticleRd.getData1();
		
		Article article = articleService.getForPrintArticle(id);
		
		return "ㅎㅇ";
		//return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if (article==null) {
			return Ut.jsHistroyBack("F-1", "게시글이 존재하지 않습니다.");
		}
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistroyBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		
		articleService.deleteArticle(id);
		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list");
		//return Ut.f("<script>alert('%d번 글이 삭제되었습니다.'); location.replace('list')</script>", id); 
	}

	@RequestMapping("/usr/article/modify")
	public String modify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(id);
		
		if (article==null) {
			return Ut.jsHistroyBack("F-1", "게시글이 존재하지 않습니다.");
		}
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		
		ResultData<Article> actorCanModifyRd = articleService.actorCanModifyRd(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		article = articleService.getForPrintArticle(id);
		return articleService.modifyArticle(id, title, body); 
	}

}