package com.jinjaeeon.boardsystem.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.manager.BoardStorageManager;
import com.jinjaeeon.boardsystem.manager.JsonFileManager;

@Service("mainService")
public class MainService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RenewalService renewal;

	@Value("${file.directory}")
	private String baseDirectory;

	private JsonFileManager jsonFile;
	private BoardStorageManager storage;	

	@PostConstruct
	public void init() {
		jsonFile = JsonFileManager.getInstance(baseDirectory);
		storage = BoardStorageManager.getInstance();			
	}

	public void startProcess()  {
		log.info("[START] KNU CIC Board Alarm System");

		// 새 글 검사 시작
		log.info("============================================================");
		log.info("============================================================");
		log.info(" <<<<<<<<<<<<<<<< 새 글 감시 프로세스가 동작 중 입니다. >>>>>>>>>>>>>>>>>");
		log.info("============================================================");
		log.info("============================================================");

		List<String> fileNameList = jsonFile.readAllJsonFile();
		if ( fileNameList!=null && fileNameList.size()!=0 ) {
			moveFileToMemory(fileNameList);
			checkNewBoardData();
		}
	}

	/**
	 * 백업파일을 메모리에 올린다.
	 * @param : 파일 이름 리스트
	 */
	private void moveFileToMemory(List<String> fileNameList) {
		for(String fileName : fileNameList) {
			BoardDTO board = null;
			try {
				board = jsonFile.readJSONFile(fileName);

				if(board != null) {
					storage.insert(board);
				}
			} catch (Exception e) {
				log.error("** 백업 파일 로딩 ERROR : ");
				log.error("** 파일 데이터가 JSON이 맞는지, KEY가 제대로 존재하는지 확인해주세요.");
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 새 글 검사 및 알림
	 */
	private void  checkNewBoardData() {
		Map<String, BoardDTO> boardList = storage.getStorage();

		for(String boardUrl : boardList.keySet() ) {
			log.info("============================================================");
			log.info(boardUrl + " -> 새 글 검사 시작");

			BoardDTO board = boardList.get(boardUrl);	
			try {
				renewal.checkNewBoardData(board);
			} catch (Exception e) {
				log.error("URL : " + boardUrl +" >> 새 글 검사 중 ERROR 발생!! \n ");
				log.error(e.getMessage());
				e.printStackTrace();
				continue;
			}

			log.info(boardUrl + " -> 새 글 검사 완료");
			log.info("============================================================");
		}
	}

}
