package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ArticleService;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired // articleService = new ArticleService();를 안해도 된다. Autowired가 연결시켜주는거
	private ArticleService articleService;
	
	// 액션 메서드
	@RequestMapping("/usr/article/detail")
	public String showDetail(int id, Model model) {
		Article article = articleService.getArticleById(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;
		int loginedMemberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		if (isLogined==false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);
		
		int id = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
		
		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int loginedMemberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		if (isLogined==false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		
		if(loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 권한이 없습니다.", id)); 
		}
		
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 게시글이 삭제되었습니다.", id),"id", id); 
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
		boolean isLogined = false;
		int loginedMemberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		if (isLogined==false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		
		ResultData<Article> actorCanModifyRd = articleService.actorCanModifyRd(loginedMemberId, article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		article = articleService.getArticleById(id);
		return articleService.modifyArticle(id, title, body); 
	}

}