package com.KoreaIT.sdy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	public int GoodPoint(int id, int memberId);

	public int getArticleLikeCount(int id);

	public Integer checkMember(int id, int memberId);

	public int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);

}
