package com.KoreaIT.sdy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ArticleRepository;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	// 생성자
	public ArticleService(ArticleRepository articleRepository) { //articleRepository = new ArticleRepository() 같은 의미
		this.articleRepository = articleRepository;
	}

	// 서비스 메서드
	public ResultData<Integer> writeArticle(String title, String body, int memberId) {
		articleRepository.writeArticle(title, body, memberId);
		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다.",id), "id", id);
				
	}

	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		
		Article article = getArticleById(id);
		
		return ResultData.from("S-1", Ut.f("%d번 게시글이 수정되었습니다.", id), "article", article); 
	}

	public ResultData<Article> actorCanModifyRd(int loginedMemberId, Article article) {
		
		if(loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-1", Ut.f("해당 게시글에 권한이 없습니다.")); 
		}
		return ResultData.from("S-1", "수정 가능");
	}
	
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

}
