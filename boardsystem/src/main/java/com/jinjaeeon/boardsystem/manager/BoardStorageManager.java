package com.jinjaeeon.boardsystem.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO; 

/**
 * 저장된 파일을 읽고 메모리에 저장한다.
 * 저장된 데이터를 관리하는 클래스.
 */
public class BoardStorageManager {

	private static BoardStorageManager boardStorageManager;
	private static Map<String, BoardDTO> storage;
	
	private BoardStorageManager() { }
	
	public static BoardStorageManager getInstance() {
		if( boardStorageManager == null ) 
			boardStorageManager = new BoardStorageManager();
		
		if( storage == null ) 
			storage = new HashMap<>();
		
		return boardStorageManager;
	}
	
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	/**
	 * Storage에서 한 개의 게시판 데이터를 가져온다.
	 * @param uri : key값으로 게시판 데이터 로딩
	 * @return
	 */
	public BoardDTO select(String uri) {
		BoardDTO board = null;
		if( storage.containsKey(uri) ) {
			board = storage.get(uri);
		} else {
			log.warn("게시판 SELECT 실패!");
			log.warn("해당 게시판은 존재하지 않습니다 : {} ", uri);
		}
		return board;
	}

	/**
	 * 전체 게시판 데이터 반환 메소드
	 * @return : storage
	 */
	public Map<String, BoardDTO> getStorage() {
		if( storage.size() == 0 )
			log.warn("Storage에 데이터가 존재하지 않습니다. ");
		return storage;
	}
	
	/**
	 * Storage에 한 개의 게시판 데이터를 추가한다.
	 * 게시판이 이미 존재한다면 overwrite.
	 * @param board : 게시판 데이터
	 */
	synchronized public void insert(BoardDTO board) {
			storage.put(board.getUri(), board);
	}
	
	/**
	 * Storage에 한 개의 게시판 데이터를 삭제한다.
	 * @param uri : key값으로 게시판 데이터 삭제
	 */
	synchronized public void delete(String uri) {
		if( storage.containsKey(uri) ) { 
			storage.remove(uri);
		}
		else {
			log.warn("게시판 DELETE 실패!");
			log.warn("해당 게시판은 존재하지 않습니다 : {} ", uri);
		}
	}

}
