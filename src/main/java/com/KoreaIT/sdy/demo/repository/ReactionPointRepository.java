package com.KoreaIT.sdy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	public int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	public int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	public int addBadReactionPoint(int actorId, String relTypeCode, int relId);

}
