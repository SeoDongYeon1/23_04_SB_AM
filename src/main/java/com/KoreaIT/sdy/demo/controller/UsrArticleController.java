package com.KoreaIT.sdy.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다",id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.",id), article);
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {		
		return articleService.getArticles();
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public Object doWrite(String title, String body) {
		int id = articleService.writeArticle(title, body);
		
		Article article = articleService.getArticleById(id);
		
		return article.getId() + "번 글이 생성되었습니다.";
		
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return id + "번 게시글은 존재하지 않습니다."; 
		}
		else {
			articleService.deleteArticle(id);
			return id + "번 게시글이 삭제되었습니다.";
		}
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return id + "번 게시글은 존재하지 않습니다."; 
		}
		else {
			articleService.modifyArticle(id, title, body);
			return id + "번 게시글이 수정되었습니다. " + article;
		}
	}

}