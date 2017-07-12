package com.jinjaeeon.boardsystem.util;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.jinjaeeon.boardsystem.common.util.FCMUtils;

public class FCMTest {

	FCMUtils fcm;
	
	@SuppressWarnings("unchecked")
	@Test
	public void sendMsgTest() {
		fcm = new FCMUtils("AAAAebwiKKo:APA91bG_3A0H_tS2eEAOoNHoWtdLOOAcHdy6e9SNFW6OJsV8k_qwXNeJSgia49606bf8rE8CcQmMeiz49oNpA4xnUSgOyvuFpCsjzp5mWbL2GPrjFZlWwFSBYQXuTcxeHJr51Aou7M5q");
		
		JSONObject json = new JSONObject();
		json.put("boardTitle", "게시판 타이틀");
		json.put("boardDataTitle", "게시글 타이틀");
		json.put("boardDataUrl", "http://www.naver.com");

		String token = "dNaNPViHbO8:APA91bH2zBn865umFF1JQli3o_qZHMIcwGH_m_dRwXiOPaO13BO5zn4knHN_uwm9vGt_5roCVZCN_bh2pqkXzoYsiKF1OqLwCykEOWRo7U5o8E-t1Kqza6N2ZvzYLErDbxZBVcS46-VO";
		
		fcm.sendNotification(json, token);
	}
	
}
