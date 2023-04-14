package com.KoreaIT.sdy.demo.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data를 해두면 @Getter, @Setter 등등 다 생긴다.
@AllArgsConstructor // 생성자를 만들때 인자를 기본적으로 생성자에 추가시켜준다. 따로 클래스에 생성자 추가 x
@NoArgsConstructor  // 생성자를 만들때 인자가 없는 생성자에 추가시켜준다. 따로 클래스에 생성자 추가 x
public class Article {
	//@Getter 쓸 필요없어짐
	private int id;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	private String title;
	private String body;
	
}