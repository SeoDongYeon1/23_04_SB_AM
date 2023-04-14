package com.KoreaIT.sdy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ArticleRepository;
import com.KoreaIT.sdy.demo.vo.Article;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	// 생성자
	public ArticleService(ArticleRepository articleRepository) { //articleRepository = new ArticleRepository() 같은 의미
		this.articleRepository = articleRepository;
	}

	// 서비스 메서드
	public int writeArticle(String title, String body) {
		articleRepository.writeArticle(title, body);
		
		return articleRepository.getLastInsertId();
	}

	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
}
