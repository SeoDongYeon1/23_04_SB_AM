package com.KoreaIT.sdy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ReactionPointRepository;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private Rq rq;

	public ResultData<Integer> actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);
		
		if(sumReactionPointByMemberId != 0) {
			return ResultData.from("F-1", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}

		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);

	}

	public ResultData<Integer> addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-1", "좋아요 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리 됨");

	}

	public ResultData<Integer> addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
		if (affectedRow != 1) {
			return ResultData.from("F-1", "좋아요 실패");
		}
		
		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 됨");
	}

	public ResultData<String> deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		}
		return ResultData.from("S-1", "좋아요 취소 됨");
	}

	public ResultData<String> deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		}
		return ResultData.from("S-1", "싫어요 취소 됨");
	}
	
	public boolean isAlreadyAddGoodRp(int articleId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(articleId, relTypeCode, rq.getLoginedMemberId());

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}

	public boolean isAlreadyAddBadRp(int articleId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(articleId, relTypeCode, rq.getLoginedMemberId());

		if (getPointTypeCodeByMemberId < 0) {
			return true;
		}
		return false;
	}
	
	private Integer getSumReactionPointByMemberId(int articleId, String relTypeCode, int actorId) {
	        Integer getSumRP = reactionPointRepository.getSumReactionPointByMemberId(articleId, relTypeCode, actorId);

			return (int) getSumRP;
		}

}