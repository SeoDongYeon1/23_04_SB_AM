package com.KoreaIT.sdy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionRepository {

	public int like_Point(int id, int memberId);

	public int getArticleLikeCount(int id);
	
}
