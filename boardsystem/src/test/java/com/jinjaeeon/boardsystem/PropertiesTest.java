package com.jinjaeeon.boardsystem;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {
	
	@Value("${file.directory}")
	private String baseDirectory;
	
	@PostConstruct
	@Test
	public void print() {
	    System.out.println(baseDirectory);
	 }
}
