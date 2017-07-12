package com.jinjaeeon.boardsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.jinjaeeon.boardsystem.service.FileWatchService;
import com.jinjaeeon.boardsystem.service.MainService;


@SpringBootApplication
@PropertySource("file:config/properties/common.properties")
public class BoardSystemApplication implements CommandLineRunner  {

	@Autowired
	FileWatchService service;

	@Autowired
	MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(BoardSystemApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		mainService();
		watchService();
	}
	
	/**
	 *  #1. 디렉토리 감시 서비스
	 */
	private void watchService() {
		Thread watchTrd = new Thread(new Runnable() {
			@Override
			public void run() {
				service.watchDir();		
			}
		});
		watchTrd.start();
	}

	/**
	 *  #2. 새 글 갱신 & 알림 서비스
	 */
	private void mainService() {
		Thread mainTrd = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					mainService.startProcess();
					try {
						Thread.sleep(1200000); // 30분
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mainTrd.start();
	}

}
