package com.jinjaeeon.boardsystem.api.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.api.board.service.BoardService;

/**
 * 게시판 관련 API
 * @author JaeeonJin
 */
@RestController
@RequestMapping("/boards")
public class BoardController {
	
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	BoardService boardService;

	/**
	 * 전체 게시판 조회
	 * @return : 게시판 리스트
	 */
	@GetMapping
	public List<BoardDTO> readBoardList() {
		log.info("모든 게시판 리스트를 조회합니다.");
		List<BoardDTO> boardList = boardService.selectAll();
		
		if( boardList != null ) log.info("모든 게시판 리스트를 조회했습니다. 게시판 개수 : {}", boardList.size());
		return boardList;
	}
	
	/**
	 * 게시판 조회
	 * @param boardUrl : 게시판 URI
	 * @return : 게시판 데이터
	 */
	@GetMapping(value="/{boardURI}")
	public BoardDTO readBoard(@PathVariable String boardURI) {
		log.info("{} 게시판을 조회합니다.", boardURI);
		BoardDTO board = boardService.selectOne(boardURI);
		
		if( board !=null ) log.info("{} 게시판을 조회했습니다.", boardURI);
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
		log.info("{} 사용자가 {} 게시판 삭제를 요청합니다.", id, boardURI);
		int result = boardService.delete(new BoardDTO(id, boardURI));
		
		if( result == 1) log.info("사용자 {}의 게시판 {}를 삭제했습니다.", id, boardURI);
		return result;
	}
	
	/**
	 * 게시판 삽입
	 * @param board : 게시판 데이터
	 * @return : 성공1, 실패0
	 */
	@PostMapping
	public int createBoard(BoardDTO board) {
		log.info("{} 사용자가 {} 게시판 추가를 요청합니다.", board.getId(), board.getUri());
		int result = boardService.insert(board);
		
		if( result == 1) log.info("{} 게시판을 사용자 {}에 추가했습니다.", board.getUri(), board.getId());
		return result;
	}
}
