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
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다",id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.",id), article);
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();
		
		return ResultData.from("S-1", "Article List", articles);
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body);
		
		int id = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		else {
			articleService.deleteArticle(id);
			return ResultData.from("S-1", Ut.f("%d번 게시글이 삭제되었습니다.", id), id); 
		}
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(int id, String title, String body) {
		Article article = articleService.getArticleById(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 존재하지 않습니다.", id)); 
		}
		else {
			articleService.modifyArticle(id, title, body);
			article = articleService.getArticleById(id);
			return ResultData.from("S-1", Ut.f("%d번 게시글이 수정되었습니다.", id), article); 
		}
	}

}