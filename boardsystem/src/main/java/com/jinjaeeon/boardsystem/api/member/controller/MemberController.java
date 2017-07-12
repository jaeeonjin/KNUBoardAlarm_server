package com.jinjaeeon.boardsystem.api.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinjaeeon.boardsystem.api.member.dto.MemberDTO;
import com.jinjaeeon.boardsystem.api.member.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 전체 멤버 조회
	 * @return : 멤버 리스트
	 */
	@GetMapping
	public List<MemberDTO> readMemberList() {
		List<MemberDTO> memberList = memberService.selectAll();
		return memberList;
	}
	
	/**
	 * 멤버 조회
	 * @param id : 멤버 ID
	 * @return : 멤버 데이터
	 */
	@GetMapping(value="/{id}")
	public MemberDTO readMember(@PathVariable String id) {
		MemberDTO member = memberService.selectOne(id);
		return member;
	}
	
	/**
	 * 멤버 삭제 (사실상 삭제는 아니고 업데이트, delete_flag (1 -> 0))
	 * @param id : 멤버 ID
	 * @return : 성공1, 실패0
	 */
	@PutMapping(value="/{id}")
	public int deleteMember(@PathVariable String id) {
		int result = memberService.delete(id);
		return result;
	}
	
	/**
	 * 멤버 삽입
	 * @param member : 멤버 데이터
	 * @return : 성공1, 실패0
	 */
	@PostMapping
	public int createMember(MemberDTO member) {
		int	result = memberService.insert(member);
		return result;
	}
	
}
