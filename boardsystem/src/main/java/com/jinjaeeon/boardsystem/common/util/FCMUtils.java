package com.jinjaeeon.boardsystem.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaeeonjin
 * FCM 유틸
 */
public class FCMUtils {

	final static private String FCM_URL = "https://fcm.googleapis.com/fcm/send";

	private final Logger log = LoggerFactory.getLogger(getClass()); 
	
	private String serverKey;
	public FCMUtils(String serverKey) {
		this.serverKey = serverKey;
	}

	public HttpURLConnection init() {
		URL url = null;
		HttpURLConnection conn = null;

		try {
			url = new URL(FCM_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + serverKey);
			conn.setRequestProperty("Content-Type", "application/json");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( conn != null ) conn.disconnect();
		}
		return conn;
	}

	@SuppressWarnings("unchecked")
	public int sendNotification(JSONObject msg, String token){
		HttpURLConnection conn;		
		
		try{
			conn = init();

			JSONObject data = new JSONObject();
			data.put("to", token.trim());
			data.put("data", msg); 

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			wr.write(data.toJSONString());
			wr.flush();

			int status = 0;
			if( null != conn ){
				status = conn.getResponseCode();
			}

			if( status != 0){
				if( status == 200 ) {
					//SUCCESS message
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					log.info("Android Notification Response {}", reader.readLine());
				} else if(status == 401) {
					//client side error
					log.error("Notification Response : Error occurred");
				} else if (status == 501) {
					//server side error
					log.error("Notification Response : [ errorCode=ServerError ] ");
				} else if ( status == 503) {
					//server side error
					log.error("Notification Response : FCM Service is Unavailable");
				} else {
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					log.error(reader.readLine());
				}
			}
			return status;
		} catch (MalformedURLException mlfexception){
			// Prototcal Error
			log.error("Error occurred while sending push Notification!.. {}", mlfexception.getMessage());
		} catch (IOException mlfexception){
			//URL problem
			log.error("Reading URL, Error occurred while sending push Notification!.. {}", mlfexception.getMessage());
		} catch (Exception exception) {
			//General Error or exception.
			log.error("Error occurred while sending push Notification!.. {}", exception.getMessage());
		}
		return -1;
	}
}
