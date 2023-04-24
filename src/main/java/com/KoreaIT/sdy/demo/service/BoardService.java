package com.KoreaIT.sdy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.sdy.demo.repository.BoardRepository;
import com.KoreaIT.sdy.demo.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	// 생성자
	public BoardService(BoardRepository boardRepository) { 
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}
}
