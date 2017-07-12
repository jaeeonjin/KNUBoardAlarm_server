package com.jinjaeeon.boardsystem.api.board.service;

import java.util.List;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;

public interface BoardService {
	int insert(BoardDTO board);
	int delete(BoardDTO board);
	List<BoardDTO> selectAll();
	BoardDTO selectOne(String boardUrl);
}
