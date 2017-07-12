package com.jinjaeeon.boardsystem.api.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jinjaeeon.boardsystem.api.member.dto.MemberDTO;
import com.jinjaeeon.boardsystem.common.dao.CommonDAO;

/**
 * 접근 TABLE : member 
 */
@Repository
public class MemberDAO extends CommonDAO {

	public List<?> selectAll() {
		return selectAll("member.selectAll");
	}
	
	public MemberDTO selectOne(String id) {
		return (MemberDTO) selectOne("member.selectOne", id);
	}

	public int delete(String id) {
		return delete("member.delete", id);
	}
	
	public int insert(MemberDTO member){
		return insert("member.insert", member);
	}
	
	public List<?> selectAllByToken(String boardUrl) {
		return selectAll("member.selectAllByToken", boardUrl);		
	}

}
