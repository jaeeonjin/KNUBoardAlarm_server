package com.jinjaeeon.boardsystem.api.member.dto;

public class FCMDTO {
	String boardTitle;
	String boardDataTitle;
	String boardDataUrl;
	String token;
	
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardDataTitle() {
		return boardDataTitle;
	}
	public void setBoardDataTitle(String boardDataTitle) {
		this.boardDataTitle = boardDataTitle;
	}
	public String getBoardDataUrl() {
		return boardDataUrl;
	}
	public void setBoardDataUrl(String boardDataUrl) {
		this.boardDataUrl = boardDataUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
