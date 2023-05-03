package com.KoreaIT.sdy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.ReplyRepository;
import com.KoreaIT.sdy.demo.util.Ut;
import com.KoreaIT.sdy.demo.vo.Article;
import com.KoreaIT.sdy.demo.vo.Reply;
import com.KoreaIT.sdy.demo.vo.ResultData;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;

	// 서비스 메서드
	public ResultData<String> writeReply(String relTypeCode, int relId, String body, int actorId) {
		replyRepository.writeReply(relTypeCode, relId, body, actorId);
		
		return ResultData.from("S-1", "댓글이 등록되었습니다.");
	}

	public List<Reply> getForPrintReplies(int relId, String relTypeCode) {
		return replyRepository.getForPrintReplies(relId, relTypeCode);
	}

}
