package com.jinjaeeon.boardsystem.common.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaeeonjin
 * HTML Parsing utils.
 * Requirement : JSoup Library ( add Dependency "jsoup" in pom.xml )
 */
public class HTMLParserUtils {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	final static int TIME_OUT = 30000; 

	/**
	 * uri에 접속해서 document 객체를 받아온다.
	 * @param uri
	 * @return
	 */
	private Document getDocument(String uri) {
		Document document = null;
		try {
			document = Jsoup.connect(uri).timeout(TIME_OUT).get();
			document.outputSettings().charset("UTF-8");
		} catch (IOException e) {
			log.error("Connection Error : " + uri);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * 받은 도큐먼트에서 css 선택자가 가리키는 데이터를 파싱한다.
	 * @param uri
	 * @param cssSelector
	 * @return
	 */
	public Elements getElements(String uri, String cssSelector) {
		Elements elements = getDocument(uri).select(cssSelector);
		return elements;
	}

}
