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
	
	public Article writeArticle(String title, String body) {
		int id = lastArticleId+1;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		lastArticleId++;
		
		return article;
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
	
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		
		return article.getId() + "번 게시글이 생성되었습니다.";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article foundArticle = null;
		
		for(Article article : articles) {
			if(article.getId()==id) {
				foundArticle=article;
				break;
			}
		}
		if(foundArticle==null) {
			return id + "번 게시글은 존재하지 않습니다."; 
		}
		else {
			articles.remove(foundArticle);
			return id + "번 게시글이 삭제되었습니다.";
		}
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article foundArticle = null;
		
		for(Article article : articles) {
			if(article.getId()==id) {
				foundArticle=article;
				break;
			}
		}
		if(foundArticle==null) {
			return id + "번 게시글은 존재하지 않습니다."; 
		}
		else {
			foundArticle.setTitle(title);
			foundArticle.setBody(body);
			return id + "번 게시글이 수정되었습니다. " + foundArticle;
		}
	}
	
}