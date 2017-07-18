package com.jinjaeeon.boardsystem.manager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.common.util.FileUtils;

/**
 * (게시판 데이터 전용) JSON 파일 입출력을 수행하는 클래스
 */
public class JsonFileManager extends FileUtils {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static JsonFileManager instance;
	private String baseDirectory;

	private JsonFileManager(String directory) {
		super(directory);
		this.baseDirectory = directory;
	}

	public synchronized static JsonFileManager getInstance(String directory) {
		if( instance == null ) {
			instance = new JsonFileManager(directory);
		}
		return instance;
	}


	/**
	 * 파일 내용이 JSON 타입인 .JSON 파일을 생성한다. 
	 * @param filePath : 파일 경로 및 이름
	 * @param board : 게시판 데이터
	 */
	public void writeJSONFile(String filePath, BoardDTO board) {
		String contents = createJSONObject(board).toJSONString();
		writeFile(contents, filePath);
	}

	/**
	 * JSON 타입의 텍스트파일을 읽기 위해 필요한 FileReader 객체를 반환하는 메소드
	 * @param filePath
	 * @return FileReader 객체
	 */
	public FileReader getFileReader(String filePath) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(baseDirectory+filePath);
			return fileReader;
		}catch(FileNotFoundException e) {
			log.error("존재하지 않는 파일입니다 : " + filePath);
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 특정 JSON 파일 1개 읽기
	 * 파일 내 data부분(게시글 url:게시글 type)을 Map으로 저장해서 반환한다.
	 * @param fileName (확장자가 포함된 file name)
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BoardDTO readJSONFile(String fileName) {
		BoardDTO dto = new BoardDTO();
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		FileReader fileReader = null;
	
		try {
			fileReader = getFileReader(fileName);
			json = (JSONObject) parser.parse(fileReader);
		} catch (IOException e) {
			log.error("파일이 존재하지 않습니다.");
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("파일 데이터가 Json이 아닙니다.");
			e.printStackTrace();
		} finally {
			if( fileReader != null )
				try {
					fileReader.close();
				} catch (IOException e) {
					log.error("FileReader.close() ERROR!");
					log.error(e.getMessage());
					e.printStackTrace();
				}
		}

		dto.setData((Map<String, String>) json.get("data"));
		dto.setUri((String) json.get("uri"));
		dto.setType((String) json.get("type"));
		return dto;
	}

	/**
	 * 파일 삭제
	 * @param fileName : 확장자 미포함
	 */
	public void removeJSONFile(String filePath) {
		deleteFile(filePath);
	}

	/**
	 * 디렉토리 내 모든 .json 파일을 가져온다.
	 * @return
	 */
	public List<String> readAllJsonFile() {
		List<String> fileNameList = readAllFile();
		return fileNameList;
	}

	/**
	 * 파일 출력을 위한 JSON 객체 생성
	 * {
	 * 		"url" : url,
	 * 		"type" : type,
	 * 		"data" : {
	 * 					"url":title,
	 * 					"url":title,
	 * 					"url":title...
	 * 				  }
	 * }
	 * @param board
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createJSONObject(BoardDTO board) {

		JSONObject json = new JSONObject();
		json.put("uri", board.getUri());
		json.put("type", board.getType());
		json.put("data", board.getData());

		return json;
	}

}
