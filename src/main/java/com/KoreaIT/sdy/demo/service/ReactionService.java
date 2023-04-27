package com.KoreaIT.sdy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ReactionRepository;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Service
public class ReactionService {
	
	@Autowired
	private ReactionRepository reactionRepository;
	
	// 생성자
	public ReactionService(ReactionRepository reactionRepository) { 
		this.reactionRepository = reactionRepository;
	}

	public ResultData<Integer> GoodPoint(int id, int memberId) {
		int affectedRow = reactionRepository.GoodPoint(id, memberId);
		
		if(affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다.", "affectedRowRd", affectedRow);
		}
		
		return ResultData.from("S-1", "좋아요 증가", "affectedRowRd", affectedRow);
	}

	public int getArticleLikeCount(int id) {
		return reactionRepository.getArticleLikeCount(id);
	}

	// 서비스 메서드

}
