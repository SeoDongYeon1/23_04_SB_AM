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
	
	@RequestMapping("/usr/reaction/doLike_Point")
	@ResponseBody
	public ResultData<?> doLike_Point(int id, int memberId) {

		ResultData<?> Like_PointRd = reactionService.like_Point(id, memberId);

		if (Like_PointRd.isFail()) {
			return Like_PointRd;
		}
		
		int likeCount = reactionService.getArticleLikeCount(id);

		ResultData<?> rd = ResultData.newData(Like_PointRd, "likeCount", likeCount);
		
		return rd;
	}

}