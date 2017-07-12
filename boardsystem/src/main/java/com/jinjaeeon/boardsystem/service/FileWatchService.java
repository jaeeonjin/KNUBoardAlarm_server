package com.jinjaeeon.boardsystem.service;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.manager.BoardStorageManager;
import com.jinjaeeon.boardsystem.manager.JsonFileManager;

/**
 * @author jaeeinjin
 * 
 * 1. 테이블 : board_info 게시판 리스트 조회
 * 2. 조회된 게시판 리스트에 대한 게시글들을 메모리로 이동
 * 3. 해당 게시글들의 새 글 파악 및 알림 주기
 * 4. 1~3 반복
 */
@Service
public class FileWatchService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private BoardStorageManager storageManager;
	private JsonFileManager jsonFile;

	@Value("${file.directory}")
	private String baseDirectory;

	@PostConstruct
	public void init() {
		storageManager = BoardStorageManager.getInstance();
		jsonFile = JsonFileManager.getInstance(baseDirectory);
	}

	/**
	 * 디렉토리 감시 메소드
	 * event.kind : ENTRY_CREATE/DELETE/MODIFY		
	 * event.context : 파일명
	 */
	@SuppressWarnings("rawtypes")
	public void watchDir() {
		WatchKey watchKey = jsonFile.getWatchKey();

		while(true) {
			for (WatchEvent event : watchKey.pollEvents()) {

				String eventKind = event.kind().toString();

				switch (eventKind) {
				// 파일이 만들어지거나, 수정될 때 자동으로 메모리에 추가됨
				case "ENTRY_CREATE" :
				case "ENTRY_MODIFY" : 
					BoardDTO dto = jsonFile.readJSONFile(event.context().toString());
					storageManager.insert(dto);
					log.info("메모리가 추가/수정되었습니다.");
					break;

				// 파일이 삭제되면 메모리에 삭제
				case "ENTRY_DELETE" :
					storageManager.delete(event.context().toString());
					break;
				}
			}

			if (!watchKey.reset()) {
				log.error("WatchService에 설정된 디렉토리가 존재하지 않습니다.");
				break;
			}
		}
	}
}

