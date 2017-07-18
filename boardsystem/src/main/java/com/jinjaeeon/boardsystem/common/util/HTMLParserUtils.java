package com.jinjaeeon.boardsystem.common.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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

	final static int TIME_OUT = 30000;
	
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	private static String BASE_URL = "http://computer.kangwon.ac.kr/index.php?mp=";
	
	private Document getDocument(String uri) {
		String url = BASE_URL + uri;
		Document document = null;
		try {
			document = Jsoup.connect(url).timeout(TIME_OUT).get();
			document.outputSettings().charset("UTF-8");
		} catch (IOException e) {
			log.error("Connection Error : " + url);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * HTML 데이터 파싱
	 * TITLE과 URL 데이터를 가져온다
	 * 가져온 데이터는 KEY:LINK Value:TITLE의 형식으로 MAP에 저장, 반환 
	 * @param url : 파싱할 웹사이트 URL
	 * @param cssQueryTag : 파싱할 노드의 태그
	 * @return
	 */
	public Map<String, String> parsing(String url, String cssSelector) {
		Map<String, String> parsedMap = new LinkedHashMap<>();
		Elements elements = getDocument(url).select(cssSelector);
		
		for(int i = 0; i < elements.size(); i++) {
			String contents_name = elements.get(i).text();
			String contents_url = elements.get(i).attr("abs:href");
			parsedMap.put(contents_url, contents_name);
		}	
		
		return parsedMap;
	}
}
