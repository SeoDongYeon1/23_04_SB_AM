package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ReactionPointService;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reaction/doGoodPoint")
	@ResponseBody
	public ResultData<?> doGoodPoint(int id, int memberId) {
		
		ResultData<Boolean> checkMemberRd =  reactionPointService.checkMember(id, memberId);
		
		if(checkMemberRd.isFail()) {
			return checkMemberRd;
		}

		ResultData<Integer> GoodPointRd = reactionPointService.GoodPoint(id, memberId);
			
		if (GoodPointRd.isFail()) {
			return GoodPointRd;
		}
			
		int likeCount = reactionPointService.getArticleLikeCount(id);
			
		ResultData<Integer> rd = ResultData.from(GoodPointRd.getResultCode(), GoodPointRd.getMsg(), "likeCount", likeCount);
		return rd;
	}

}