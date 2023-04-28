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
	public ResultData<Integer> writeArticle(String title, String body, int memberId, int boardId) {
		articleRepository.writeArticle(title, body, memberId, boardId);
		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다.",id), "id", id);
				
	}

	public Article getForPrintArticle(int id) {
		
		return articleRepository.getForPrintArticle(id);
	}
	
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);
		
		updateForPrintData(actorId, article);
		
		return article;
	}
	
	private void updateForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData<String> actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());
	}

	private ResultData<String> actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}
	
	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public String modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		
		return Ut.jsReplace(Ut.f("%d번 게시글이 수정되었습니다.", id),Ut.f("../article/detail?id=%d", id)); 
	}

	public ResultData<String> actorCanModifyRd(int loginedMemberId, Article article) {
		
		if(loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-1", Ut.f("%d번 게시글에 권한이 없습니다.", article.getId()));
		}
		return ResultData.from("S-1", "수정 가능");
	}
	
	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchKeywordTypeCode, String searchKeyword) {
		int limitFrom = (page - 1) * itemsInAPage;
		
		return articleRepository.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom, itemsInAPage);
	}

	public int articlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.articlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}
	
	public ResultData<Integer> increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);
		
		if(affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다.", "affectedRowRd", affectedRow);
		}
		
		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}

	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReactionPoint(relId);
		
		if(affectedRow==0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}
	
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseBadReactionPoint(relId);
		
		if(affectedRow==0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReactionPoint(relId);
		
		if(affectedRow==0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

}
