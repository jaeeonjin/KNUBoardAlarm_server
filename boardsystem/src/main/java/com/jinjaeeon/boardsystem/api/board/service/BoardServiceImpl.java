package com.jinjaeeon.boardsystem.api.board.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.board.dao.BoardDAO;
import com.jinjaeeon.boardsystem.api.board.dto.BoardDTO;
import com.jinjaeeon.boardsystem.common.util.HTMLParserUtils;
import com.jinjaeeon.boardsystem.manager.JsonFileManager;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Value("${file.directory}")
	private String baseDirectory;

	@Value("${board.common}")
	private String commonBoard;

	@Value("${board.lecture}")
	private String lectureBoard;

	private HTMLParserUtils parser;
	private JsonFileManager jsonFile;
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@PostConstruct
	public void init() {
		jsonFile = JsonFileManager.getInstance(baseDirectory);
		parser = new HTMLParserUtils();
	}
	
	@Override
	public List<BoardDTO> selectAll() {
		@SuppressWarnings("unchecked")
		List<BoardDTO> list = (List<BoardDTO>) boardDAO.selectAll();

		if( list.size() == 0 )
			log.error("가져올 데이터가 없습니다.");

		return list;
	}

	@Override
	public BoardDTO selectOne(String boardURI) {
		BoardDTO board = boardDAO.selectOne(boardURI);

		if(board == null)
			log.error("boardURI : " + boardURI + "게시판이 존재하지 않습니다.");

		return board;
	}

	/**
	 * #1. 트리거 실행 
	 * #2. (Watch Service) 파일을 지우고 메모리에서도 지움.
	 */
	@Override
	public int delete(BoardDTO board) {
		int result = boardDAO.delete(board);

		if(result == 0)
			log.error("지울 게시판 데이터가 없습니다. boardURI : " + board.getUri());
		
		else {
			jsonFile.deleteFile(board.getUri());
		}

		return result;
	}

	/**
	 * 프로시저 CALL : BOARD_INSERT
	 * @return 에러:0, 정상:1
	 */
	@Override
	public int insert(BoardDTO board) {
		int result = 0;

		String boardType;
		if(board.getUri().startsWith("3_"))
			boardType = lectureBoard;
		else
			boardType = commonBoard;
		board.setType(boardType);

		try {
			try {
				result = boardDAO.insert(board);
				System.err.println(board);
			} catch(Exception e) {
				log.error("외래키 제약 조건 ERROR");
				log.error("ID : " + board.getId() + " Board_URI : " + board.getUri() );
				e.printStackTrace();
				throw e;
			}

			// 디렉토리 내에 게시판 파일이 존재하지 않으면
			// 1. 게시판 파싱
			// 2. 데이터 파일 출력
			// 3. // 디렉토리 감시 중 파일 추가될 때 자동으로 메모리에 추가 (물론, 메모리에 존재하지 않을 떄)
			try {
				boolean flag = jsonFile.isFile(board.getUri());
				if( !flag ) {
					// #1
					Map<String, String> boardMap = parser.parsing(board.getUri(), board.getType());
					board.setData(boardMap);

					// #2
					jsonFile.writeJSONFile(board.getUri(), board);
				}
			} catch(Exception e) {
				log.error("게시판 파싱 OR 파일 출력 오류");
				e.printStackTrace();
				
				// 롤백
				if( jsonFile.isFile(board.getUri())) {
					jsonFile.deleteFile(board.getUri());
				}
				throw e;
			}
		}
		catch( Exception e ) { 
			// none
		}
		
		return result;
	}

}
