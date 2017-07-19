package com.jinjaeeon.boardsystem.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.common.util.HTMLParserUtils;

@Service
public class HTMLParserService {
	
	private final static String BASE_URL = "http://computer.kangwon.ac.kr/index.php?mp=";
	private HTMLParserUtils parser;
	
	public HTMLParserService() {
		this.parser = new HTMLParserUtils();
	}
	
	/**
	 * HTML 데이터 파싱
	 * TITLE과 URL 데이터를 가져온다
	 * 가져온 데이터는 KEY:LINK Value:TITLE의 형식으로 MAP에 저장, 반환 
	 * @param url : 파싱할 웹사이트 URL
	 * @param cssQueryTag : 파싱할 노드의 태그
	 * @return
	 */
	public Map<String, String> parsing(String uri, String cssSelector) {
		Map<String, String> parsedMap = new LinkedHashMap<>();
		Elements elements = parser.getElements(BASE_URL+uri, cssSelector);
		
		for(int i = 0; i < elements.size(); i++) {
			String contents_name = elements.get(i).text();
			String contents_uri = elements.get(i).attr("abs:href");
			parsedMap.put(contents_uri, contents_name);
		}	
		
		return parsedMap;
	}
	
}
