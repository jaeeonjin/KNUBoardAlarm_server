package com.jinjaeeon.boardsystem.api.member.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.board.dao.MemberBoardDAO;
import com.jinjaeeon.boardsystem.api.member.dao.MemberDAO;
import com.jinjaeeon.boardsystem.api.member.dto.MemberDTO;
import com.jinjaeeon.boardsystem.service.fcm.FCMDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	MemberBoardDAO memberBoardDAO;
	
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@Override
	public List<MemberDTO> selectAll() {
		@SuppressWarnings("unchecked")
		List<MemberDTO> list = (List<MemberDTO>) memberDAO.selectAll();

		if ( list.size() == 0 )
			log.error("가져올 데이터가 없습니다.");
		
		return list;
	}

	@Override
	public MemberDTO selectOne(String id) {
		MemberDTO member = memberDAO.selectOne(id);
		
		if(member==null)
			log.error("ID : " + id + " 멤버가 존재하지 않습니다.");
		
		return member;
	}

	@Override
	public int delete(String id) {
		int result = memberDAO.delete(id);
	
		if(result == 0)
			log.error("지울 멤버 데이터가 없습니다. ID : " + id);
		
		return result;
	}

	@Override
	public int insert(MemberDTO member) {
		int result = 0;
		
		try {
			result = memberDAO.insert(member);
		} catch(Exception e) {
			log.error("멤버 데이터 삽입에 실패했습니다.");
			log.error("ID or Token 값 중복");
			log.error("ID : {}", member.getId());
			log.error("Token : {}", member.getToken());
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FCMDTO> selectAllByToken(String boardUri) {
		return (List<FCMDTO>) memberDAO.selectAllByToken(boardUri);
	}

}
