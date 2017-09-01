package com.jinjaeeon.boardsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("file:config/properties/common.properties")
public class BoardSystemApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BoardSystemApplication.class, args);
	}

}
