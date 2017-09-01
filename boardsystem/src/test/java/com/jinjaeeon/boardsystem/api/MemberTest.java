package com.jinjaeeon.boardsystem.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jinjaeeon.boardsystem.api.member.controller.MemberController;
import com.jinjaeeon.boardsystem.api.member.service.MemberService;

@RunWith(SpringRunner.class) 
@WebMvcTest(MemberController.class)
public class MemberTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	public MemberService memberService;
	
	@Test
	public void insertTest() throws Exception {

	}
}
