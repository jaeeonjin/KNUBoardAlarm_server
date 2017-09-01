package com.jinjaeeon.boardsystem.util;

import static org.junit.Assert.assertEquals;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.jinjaeeon.boardsystem.common.util.HTMLParserUtils;

public class HTMLParserTest {

	private HTMLParserUtils util;
	private String testURI = "http://computer.kangwon.ac.kr/index.php?mp=6_1_1";
	private String testCssSelector = "table.bbs_list tbody td.tit a";
	
	@Before
	public void init() {
		util = new HTMLParserUtils();
	}

	@Test
	public void getElements() {
		Elements elements = util.getElements(testURI, testCssSelector);
		assertEquals(true, elements == null ? false : true);
	}
}
