package com.KoreaIT.sdy.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UsrHomeController {
	
	private int count;
	
	public UsrHomeController() {
		count = 0;
	}
	
	@RequestMapping("/usr/home/getCount")
	@ResponseBody
	public int getCount() {
		return count++;
	}
	
	@RequestMapping("/usr/home/setCount")
	@ResponseBody
	public String setCount(int count) {
		this.count = count;
		return "count의 값이 "+ this.count +"으로 초기화 됩니다.";
	}
	
	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() {
		return 10;
	}
	
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
		return "안녕";
	}
	
	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 10.5;
	}
	
	@RequestMapping("/usr/home/getFloat")
	@ResponseBody
	public float getFloat() {
		return 10.5f;
	}
	
	@RequestMapping("/usr/home/getChar")
	@ResponseBody
	public char getChar() {
		return 'a';
	}
	
	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수나이", 20);
		map.put("영희나이", 10);
		
		return map;
	}
	
	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<String> getList() {
		List<String> list = new ArrayList<>();
		list.add("철수나이");
		list.add("영희나이");
		return list;
	}
	
	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article article = new Article(1,"ㅎㅇ","asd");
		
		return article;
	}
	
	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		Article article1 = new Article(1,"ㅎㅇ1","asd1");
		Article article2 = new Article(2,"ㅎㅇ2","asd2");
		
		List<Article> articles = new ArrayList<>();
		articles.add(article1);
		articles.add(article2);
		
		return articles;
	}
	
}

@Data // @Data를 해두면 @Getter, @Setter 등등 다 생긴다.
@AllArgsConstructor // 생성자를 만들때 인자를 기본적으로 생성자에 추가시켜준다. 따로 클래스에 생성자 추가 x
@NoArgsConstructor  // 생성자를 만들때 인자가 없는 생성자에 추가시켜준다. 따로 클래스에 생성자 추가 x
class Article {
	//@Getter 쓸 필요없어짐
	private int id;
	private String title;
	private String body;
	
}