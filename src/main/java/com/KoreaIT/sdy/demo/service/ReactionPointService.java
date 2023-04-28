package com.KoreaIT.sdy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ReactionPointRepository;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	
	// 생성자
	public ReactionPointService(ReactionPointRepository reactionPointRepository) { 
		this.reactionPointRepository = reactionPointRepository;
	}

	public ResultData<Integer> GoodPoint(int id, int memberId) {
		int affectedRow = reactionPointRepository.GoodPoint(id, memberId);
		
		if(affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다.", "affectedRowRd", affectedRow);
		}
		
		return ResultData.from("S-1", "좋아요 증가", "affectedRowRd", affectedRow);
	}

	public int getArticleLikeCount(int id) {
		return reactionPointRepository.getArticleLikeCount(id);
	}

	public ResultData<Boolean> checkMember(int id, int memberId) {
		Integer GoodPoint = reactionPointRepository.checkMember(id, memberId);
		
		if(GoodPoint==null) {
			GoodPoint = 0;
		}
		
		if(GoodPoint==1) {
			return ResultData.from("F-1", "이미 좋아요를 누른 상태입니다.");
		}
		else {
			return ResultData.from("S-1", "좋아요 가능", "true", true);
		}
		
	}

	public boolean actorCanMakeReaction(int actorId, String relTypeCode, int relId) {		
		return reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

}
