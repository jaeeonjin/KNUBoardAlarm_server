//package com.jinjaeeon.boardsystem.util;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardWatchEventKinds;
//import java.nio.file.WatchEvent;
//import java.nio.file.WatchKey;
//import java.nio.file.WatchService;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.jinjaeeon.boardsystem.common.util.FileUtils;
//
//public class FileTest {
//
//	private FileUtils util;
//
//	private String baseDirectory;
//
//	@Before
//	public void init() {
//		baseDirectory = "D:/board/";
//		//util = new FileUtils(baseDirectory);
//	}
//
//	//@Test
//	public void readAllFile() {
//		List<String> list = util.readAllFile();
//		assertNotNull(list);
//	}
//
//	//@Test
//	public void deleteFile() {
//		boolean flag = util.deleteFile("test.board");
//		assertEquals(flag, true);
//	}
//
//	//@Test
//	public void writeFile() {
//		String fileName = "test.txt";
//		util.writeFile("TEST", fileName);
//		File file = new File(baseDirectory+fileName);
//		assertEquals(file.isFile(), true);
//	}
//
//	//@Test
//	public void isFileTest() {
//		File file = new File(baseDirectory+"test1.txt");
//		assertEquals(file.isFile(), false);
//	}
//
//}
