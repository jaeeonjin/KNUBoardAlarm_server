package com.jinjaeeon.boardsystem.api.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.api.board.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	/**
	 * 전체 게시판 조회
	 * @return : 게시판 리스트
	 */
	@GetMapping
	public List<BoardDTO> readBoardList() {
		List<BoardDTO> boardList = boardService.selectAll();
		return boardList;
	}
	
	/**
	 * 게시판 조회
	 * @param boardUrl : 게시판 URI
	 * @return : 게시판 데이터
	 */
	@GetMapping(value="/{boardURI}")
	public BoardDTO readBoard(@PathVariable String boardURI) {
		BoardDTO board = boardService.selectOne(boardURI);
		return board;
	}
	
	/**
	 * 게시판 삭제
	 * @param id : 사용자 ID
	 * @param boardURI : 게시판 URI
	 * @return : 성공1, 실패0
	 */
	@DeleteMapping(value="{id}/{boardURI}")
	public int deleteBoard(@PathVariable String id, @PathVariable String boardURI) {
		int result = boardService.delete(new BoardDTO(id, boardURI));
		return result;
	}
	
	/**
	 * 게시판 삽입
	 * @param board : 게시판 데이터
	 * @return : 성공1, 실패0
	 */
	@PostMapping
	public int createBoard(BoardDTO board) {
		int result = boardService.insert(board);
		return result;
	}
}
