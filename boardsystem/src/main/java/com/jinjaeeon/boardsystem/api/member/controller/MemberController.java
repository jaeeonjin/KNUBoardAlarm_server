package com.jinjaeeon.boardsystem.api.member.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinjaeeon.boardsystem.api.member.dto.FCMDTO;
import com.jinjaeeon.boardsystem.api.member.dto.MemberDTO;
import com.jinjaeeon.boardsystem.api.member.service.MemberService;

/**
 * 회원 관련 API
 * @author JaeeonJin
 */
@RestController
@RequestMapping("/members")
public class MemberController {

	private final Logger log = LoggerFactory.getLogger(getClass()); 

	@Autowired
	private MemberService memberService;
	
	/**
	 * 전체 멤버 조회
	 * @return : 멤버 리스트
	 */
	@GetMapping
	public List<MemberDTO> readMemberList() {
		log.info("등록된 모든 멤버를 조회합니다.");
		List<MemberDTO> memberList = memberService.selectAll();
		
		log.info("등록된 멤버 수 : {}", memberList.size());
		return memberList;
	}
	
	/**
	 * 멤버 조회
	 * @param id : 멤버 ID
	 * @return : 멤버 데이터
	 */
	@GetMapping(value="/{id}")
	public MemberDTO readMember(@PathVariable String id) {
		log.info("아이디 : {}의 멤버를 조회합니다.", id);
		MemberDTO member = memberService.selectOne(id);
		log.info("조회한 아이디를 가진 회원 정보 : {} ", member);
		return member;
	}
	
	/**
	 * 멤버 삭제 (사실상 삭제는 아니고 업데이트, delete_flag (1 -> 0))
	 * @param id : 멤버 ID
	 * @return : 성공1, 실패0
	 */
	@PutMapping(value="/{id}")
	public int deleteMember(@PathVariable String id) {
		log.info("아이디 : {}의 멤버를 삭제합니다.", id);
		int result = memberService.delete(id);
		
		if( result==1 ) log.info("아이디 : {}의 멤버를 삭제했습니다.", id);
		return result;
	}
	
	/**
	 * 멤버 삽입
	 * @param member : 멤버 데이터
	 * @return : 성공1, 실패0
	 */
	@PostMapping
	public int createMember(MemberDTO member) {
		log.info("아이디 : {}의 멤버를 추가합니다.", member.getId());
		int	result = memberService.insert(member);
		if( result == 1) log.info("아이디 : {}의 멤버를 추가했습니다.", member.getId());
		return result;
	}
	
	/**
	 * 토큰 리스트 조회
	 */
	@GetMapping(value="/tokens/{boardURI}")
	public List<FCMDTO> selelctTokenAndBoardNameByBoardURI(@PathVariable String boardURI) {
		List<FCMDTO> result = memberService.selectAllByToken(boardURI);
		return result;
	}
}
