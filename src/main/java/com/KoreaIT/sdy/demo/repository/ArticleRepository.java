package com.KoreaIT.sdy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.sdy.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	// INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}
	@Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}")
	public void writeArticle(String title, String body);
	
	// SELECT * FROM article ORDER BY id DESC
	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();
	
	// SELECT * FROM article WHERE id = #{id}
	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticleById(int id);
	
	// DELETE FROM article WHERE id = #{id}
	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);
	
	// UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}
	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();
	
	
}
