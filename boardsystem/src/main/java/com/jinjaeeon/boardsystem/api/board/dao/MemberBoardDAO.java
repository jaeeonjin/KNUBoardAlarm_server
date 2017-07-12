package com.jinjaeeon.boardsystem.api.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.common.dao.CommonDAO;

/**
 * 접근 TABLE : member_board 
 */
@Repository
public class MemberBoardDAO extends CommonDAO {

	public int insert(BoardDTO board) {
		return insert("memberBoard.insert", board);
	}
	
	public int delete(BoardDTO board) {
		return delete("memberBoard.delete", board);
	}
	
	public List<?> selectAll() {
		return selectAll("memberBoard.selectAll");
	}
	
	public BoardDTO selectOne(BoardDTO board) {
		return (BoardDTO) selectOne("memberBoard.selectOne", board);
	}
	
	public List<?> selectByURI(String boardURI) {
		return selectAll("memberBoard.selectOne", boardURI);
	}
	
}
 