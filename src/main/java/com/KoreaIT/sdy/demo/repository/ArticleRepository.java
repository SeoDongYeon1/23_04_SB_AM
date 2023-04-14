package com.KoreaIT.sdy.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.KoreaIT.sdy.demo.vo.Article;

@Component
public class ArticleRepository {
	private int lastArticleId;
	private List<Article> articles;
	
	// 생성자
	public ArticleRepository() {
		articles = new ArrayList<>();
		lastArticleId = 0;
	}
	
	
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);
		lastArticleId++;
		return article;
	}

	public void deleteArticle(int id) {
		Article article = getArticleById(id);
		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticleById(id);
		article.setTitle(title);
		article.setBody(body);
	}
	
	public Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	
	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}
}
