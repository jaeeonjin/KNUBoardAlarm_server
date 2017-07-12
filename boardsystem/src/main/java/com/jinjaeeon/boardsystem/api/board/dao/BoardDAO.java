package com.jinjaeeon.boardsystem.api.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.common.dao.CommonDAO;

/**
 * 접근 TABLE : board_info 
 */
@Repository
public class BoardDAO extends CommonDAO {

	public List<?> selectAll() {
		return selectAll("board.selectAll");
	}

	public BoardDTO selectOne(String boardURI) {
		return (BoardDTO) selectOne("board.selectOne", boardURI);
	}

	public int delete(BoardDTO board) {
		return delete("board.delete", board);
	}

	/**
	 * 프로시저 CALL, 결과값 SELECT
	 * @param board
	 * @return
	 */
	public int insert(BoardDTO board) {
		insert("board.insert", board);
		return (int) selectOne2("board.getResult");
	}
	
}
