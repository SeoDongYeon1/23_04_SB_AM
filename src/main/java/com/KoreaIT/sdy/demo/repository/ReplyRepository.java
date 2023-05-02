package com.KoreaIT.sdy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyRepository {

	void writeReply(String relTypeCode, int relId, String body, int actorId);

}
