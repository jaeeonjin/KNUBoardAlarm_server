package com.jinjaeeon.boardsystem.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.common.util.HTMLParserUtils;
import com.jinjaeeon.boardsystem.manager.BoardStorageManager;
import com.jinjaeeon.boardsystem.manager.JsonFileManager;
import com.jinjaeeon.boardsystem.service.fcm.FCMService;

/**
 * 새 글 검사 및 데이터 갱신 관련 클래스
 * 특히, [검사]는 서버 시작부터 주기적으로 실행되어야한다.( 반복 Thread ) 
 */
@Service
public class RenewalService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	HTMLParserUtils parser;
	BoardStorageManager storageManager;
	JsonFileManager jsonFile;
	
	@Value("${file.directory}")
	private String baseDirectory;
	
	@Autowired
	FCMService fcm;

	@PostConstruct
	public void init() {
		parser = new HTMLParserUtils();
		storageManager = BoardStorageManager.getInstance();
		jsonFile = JsonFileManager.getInstance(baseDirectory);
	}
	
	/**
	 * 특정 게시판에 새로운 게시글이 있는지 검사한다.
	 * @param map
	 * @throws Exception 
	 */
	public void checkNewBoardData(BoardDTO board) throws Exception {

		BoardDTO beforeBoard;
		Map<String, String> parsedData;

		String boardUri = board.getUri();

		// 1. 해당 URL의 게시판을 파싱한다.
		parsedData = parser.parsing(boardUri, board.getType());
		log.info("게시판 데이터를 파싱했습니다. uri : " + boardUri);
		log.info("데이터 개수 : " + parsedData.size());

		// 2. 기존 URL의 게시판 정보를 로딩한다.
		beforeBoard = storageManager.select(boardUri);

		// 2-1. 내부 메모리에 게시판이 이미 존재한다면
		if( beforeBoard != null ) { 

			// 3. 기존 게시판 데이터와 새로 파싱한 데이터를 비교한다
			equalBoardData(boardUri, beforeBoard.getData(), parsedData);
			log.info("새 글 검사를 완료했습니다.");

			// 4. 모든 업데이트를 끝내고 내부 저장소를 갱신한다.
			// 이 코드는 필요가 없을 뿐더러 에러를 발생시킨다..
			// BeforeMap은 Storage안에 있는 맵을 참조하는 오브젝트.
			// 따라서 이미 equalBoardData 메소드 수행 중 업데이트 사항이 그대로 반영됨.
		}

		// 2-2. 내부 메모리에 게시판이 존재하지 않는다면
		else {
			// 3. 서버 내부 메모리에 저장(파일이 추가되면 자동으로 수행)
			// storageManager.insert(beforeBoard); 
			// log.info("새로운 게시판을 [서버 메모리]에 추가했습니다.");
		}

		// 공통
		jsonFile.writeJSONFile(boardUri, beforeBoard);
		log.info("파일이 출력되었습니다 >> " + boardUri + ".json");
	}

	/**
	 * 파싱 데이터와 기존 데이터를 비교하여 갱신 작업 및 푸쉬 알림을 보낸다.
	 * @param beforeBoard : STORAGE에 저장된 게시판
	 * @param parsedData : 새로 파싱한 게시판 데이터
	 * @throws Exception 
	 */
	private void equalBoardData(String boardUri, Map<String, String> beforeData, Map<String, String> parsedData) {
		for(String key : parsedData.keySet()) { 
			// 존재하지 않는 키 -> 새로운 게시글
			if( !beforeData.containsKey(key) ) { 
				log.info("새로운 데이터 >> " + parsedData.get(key));

				// FCM 발신
				fcm.sendMessage(boardUri, key, parsedData.get(key));

				// 새로운 글이 발견되었으므로 beforeMap을 갱신한다.(overwrite)
				beforeData.put(key, parsedData.get(key));
			}
		}
	}

}
