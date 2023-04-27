package com.KoreaIT.sdy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.sdy.demo.service.ReactionService;
import com.KoreaIT.sdy.demo.vo.ResultData;
import com.KoreaIT.sdy.demo.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionService reactionService;

	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reaction/doGoodPoint")
	@ResponseBody
	public ResultData<Integer> doGoodPoint(int id, int memberId) {

		ResultData<Integer> GoodPointRd = reactionService.GoodPoint(id, memberId);

		if (GoodPointRd.isFail()) {
			return GoodPointRd;
		}
		
		int likeCount = reactionService.getArticleLikeCount(id);

		ResultData<Integer> rd = ResultData.from(GoodPointRd.getResultCode(), GoodPointRd.getMsg(), "likeCount", likeCount);
		
		return rd;
	}

}