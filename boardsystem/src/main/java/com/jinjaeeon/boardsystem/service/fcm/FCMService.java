package com.jinjaeeon.boardsystem.service.fcm;

import java.util.List;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jinjaeeon.boardsystem.api.member.service.MemberService;
import com.jinjaeeon.boardsystem.common.util.FCMUtils;

@Service
public class FCMService {

	@Autowired
	MemberService memberService;
	
	@Value("${fcm.serverkey}")
	String serverKey;
	
	FCMUtils fcm;
	
	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@PostConstruct
	public void init() {
		fcm = new FCMUtils(serverKey);
	}
		
	/**
	 * 특정 게시판을 추가한 사용자들을 조회한 후, 그 사용자들에게 FCM을 보낸다.
	 * @param boardUri : 게시판 uri
	 * @param boardTitle
	 */
	public void sendMessage(String boardUri, String boardDataUrl, String boardDataTitle) {	
		// 해당 게시판을 추가한 멤버토큰 / 멤버게시판이름 조회
		List<FCMDTO> tokenList = memberService.selectAllByToken(boardUri);
		if(tokenList!=null && tokenList.size()!=0)
			for(FCMDTO fcm : tokenList) {

				// 푸쉬 데이터 설정
				fcm.setBoardDataUrl(boardDataUrl);
				fcm.setBoardDataTitle(boardDataTitle);

				// 푸쉬 알림 전송
				// 스레드 사용 이유 : 스레드로 처리하지 않으면 일부 알람은 수신받지 못함. (최소 1초..)
				new Thread( new Runnable() {
					@Override
					public void run() {
						sendMessage(fcm);
					}
				}).start();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error("** Fcm Thread Error : " + e.getMessage());
				} 
			}
	}
	
	/**
	 * 메세지 형태를 Json 타입으로 재구성해서 푸쉬 전송
	 * json Key에 맟춰 클라이언트(Android)에서 키로 값을 얻어 사용한다.
	 * @param dto
	 */
	@SuppressWarnings("unchecked")
	private void sendMessage(FCMDTO dto) {
		JSONObject jsonMsg = new JSONObject();
		
		String boardTitle = dto.getBoardTitle();
		String boardDataUrl = dto.getBoardDataUrl();
		String boardDataTitle = dto.getBoardDataTitle();
		String token = dto.getToken();
		
		jsonMsg.put("boardTitle", boardTitle);
		jsonMsg.put("boardDataUrl", boardDataUrl);
		jsonMsg.put("boardDataTitle", boardDataTitle);
		
		fcm.sendNotification(jsonMsg, token);
	}
}
