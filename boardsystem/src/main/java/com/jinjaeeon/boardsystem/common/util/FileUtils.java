package com.jinjaeeon.boardsystem.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaeeonjin
 * 파일 입출력 유틸 클래스
 */
public class FileUtils {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private String baseDirectory;

	public FileUtils(String directory) {
		this.baseDirectory = directory;
		makeDirectory();
	}

	/**
	 * 디렉토리 폴더 생성 메소드
	 */
	private void makeDirectory() {
		File path = new File(baseDirectory);
		if( !path.isDirectory() )
			path.mkdir();
	}


	/**
	 * 파일 쓰기 메소드
	 * @param content : 파일에 저장할 데이터
	 * @param fileName : 파일 이름
	 * @param fileType : 파일 확장자 종류
	 */
	public void writeFile(String content, String filePath) {
		FileWriter file;
		try {
			file = new FileWriter(baseDirectory+filePath);
			file.write(content);
			file.flush();
			file.close();
		} catch(IOException e) {
			log.error("파일 쓰기 ERROR : " + filePath);
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * baseDirectory 내의 모든 파일들의 이름을 저장하고 반환하는 메소드.
	 * 전체 파일을 읽을 때 사용한다.
	 * @return 파일 이름 리스트
	 */
	public List<String> readAllFile() {
		List<String> fileNameList = new ArrayList<>();

		File fileList[] = new File(baseDirectory).listFiles();
		if( fileList.length == 0 ) { 
			log.warn(baseDirectory+"내에 존재하는 파일이 없습니다.");
			return null;
		}

		for(File file : fileList) {
			fileNameList.add( file.getName() );
			log.info("파일 로딩 : " + file.getName());
		}

		return fileNameList;
	}

	/**
	 * 파일 삭제 메소드
	 * @param filePath
	 * @return true : 파일 삭제 O / false : 파일 삭제 X
	 */
	public boolean deleteFile(String filePath) {
		File file = new File(baseDirectory+filePath);
		boolean flag = file.delete();

		if( !flag ) {
			log.error("존재하지 않는 파일입니다.");
		}
		
		return flag;
	}

	public boolean isFile(String filePath) {
		File file = new File(baseDirectory+filePath);
		return file.isFile();
	}

	/**
	 * 디렉토리 모니터링 WatchKey 객체 반환 메소드
	 */
	public WatchKey getWatchKey() {
		WatchService watchService = null;
		WatchKey watchKey = null;
		Path path = null;

		try {
			watchService = FileSystems.getDefault().newWatchService();

			//모니터링을 원하는 디렉토리 Path를 얻는다.
			path = Paths.get(baseDirectory);    

			//모니터링 서비스를 할 path에 의해 파일로케이션을 등록
			watchKey = path.register(watchService, 
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
		} catch (IOException e) {
			log.error("WatchService 오류 " + e.getMessage());
			e.printStackTrace();
		}

		return watchKey;
	}

}
