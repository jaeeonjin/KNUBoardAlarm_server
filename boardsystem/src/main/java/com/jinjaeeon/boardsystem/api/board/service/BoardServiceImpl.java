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
import com.jinjaeeon.boardsystem.service.FileService;
import com.jinjaeeon.boardsystem.service.HTMLParserService;

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

	@Autowired
	private HTMLParserService parser;

	private FileService fileService;
	private final Logger log = LoggerFactory.getLogger(getClass()); 

	@PostConstruct
	public void init() {
		fileService = FileService.getInstance(baseDirectory);
	}

	@Override
	public List<BoardDTO> selectAll() {
		@SuppressWarnings("unchecked")
		List<BoardDTO> list = (List<BoardDTO>) boardDAO.selectAll();

		if( list.size() == 0 || list == null ) log.warn("디렉토리 내에 가져올 게시판 데이터가 없습니다.");
		return list;
	}

	@Override
	public BoardDTO selectOne(String boardURI) {
		BoardDTO board = boardDAO.selectOne(boardURI);

		if(board == null) log.error("boardURI : {} 게시판이 존재하지 않습니다.", boardURI);
		return board;
	}

	/**
	 * 트리거와 함께 실행된다. 
	 */
	@Override
	public int delete(BoardDTO board) {
		int result = boardDAO.delete(board);
		
		if(result == 0) log.error("지울 게시판 데이터가 없습니다. boardURI : {}", board.getUri());
		return result;
	}

	/**
	 * 프로시저 CALL : BOARD_INSERT
	 * @return 에러:0, 정상:1
	 */
	@Override
	public int insert(BoardDTO board) {
		int result = 0;

		/*
		 * TODO : 이 부분을 어떻게 처리하느냐가 문제로다
		 * 기존의 두개의 태그 값이 변경되는 것은 상관없다(-> 외부 프로퍼티로 조작하면 된다.)
		 * 하지만, 새로운 타입의 태그를 추가할 필요가 있다면 어떻게 할 것인가?..
		 */
		String boardType;
		if(board.getUri().startsWith("3_")) boardType = lectureBoard;
		else boardType = commonBoard;
		board.setType(boardType);

		try {
			result = boardDAO.insert(board);
			if( result == 0 ) throw new Exception();
		} catch(Exception e) {
			log.error("게시판 데이터 INSERT 에러");
			log.error("ID : {},  Board_URI : {}", board.getId(), board.getUri() );
			e.printStackTrace();
			return result;
		}

		// 디렉토리 내에 게시판 파일이 존재하지 않으면
		// 1. 게시판 파싱
		// 2. 데이터 파일 출력
		try {
			boolean flag = fileService.isFile(board.getUri());
			if( !flag ) {
				// #1
				log.info("새로운 게시판 정보입니다. 파일 추가를 시작합니다.");
				Map<String, String> boardMap = parser.parsing(board.getUri(), board.getType());
				board.setData(boardMap);

				// #2
				fileService.writeJSONFile(board.getUri(), board);
				log.info("새로운 파일을 추가했습니다.");
			}
		} catch(Exception e) {
			log.error("게시판 파싱 도중 파일 출력 ERROR");
			e.printStackTrace();

			// 롤백
			if( fileService.isFile(board.getUri())) {
				fileService.deleteFile(board.getUri());
			}
		}

		return result;
	}

}
