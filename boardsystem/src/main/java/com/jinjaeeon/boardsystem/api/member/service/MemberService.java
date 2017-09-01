package com.jinjaeeon.boardsystem.api.member.service;

import java.util.List;

import com.jinjaeeon.boardsystem.api.member.dto.FCMDTO;
import com.jinjaeeon.boardsystem.api.member.dto.MemberDTO;

public interface MemberService {
	List<MemberDTO> selectAll();
	MemberDTO selectOne(String id);
	int delete(String id);
	int insert(MemberDTO member);
	List<FCMDTO> selectAllByToken(String boardURI);
}
