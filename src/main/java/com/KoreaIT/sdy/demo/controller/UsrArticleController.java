package com.KoreaIT.sdy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {
	
	int lastArticleId;
	List<Article> articles; 
	
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}
	
	private void makeTestData() {
		for(int i = 1; i <= 10; i++) {
			String title = "제목"+i;
			String body = "내용"+i;
			
			writeArticle(title, body);
		}
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article article = new Article(1,"ㅎㅇ","asd");
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {		
		return articles;
	}
	
	public Article writeArticle(String title, String body) {
		int id = lastArticleId+1;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		lastArticleId++;
		
		return article;
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		
		return article.getId() + "번 게시글이 생성되었습니다.";
	}
	
}