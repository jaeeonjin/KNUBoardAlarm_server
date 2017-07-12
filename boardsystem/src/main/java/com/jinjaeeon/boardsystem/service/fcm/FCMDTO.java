package com.jinjaeeon.boardsystem.service.fcm;

public class FCMDTO {

	private String boardTitle; // 게시판 이름
	private String boardDataUrl; // 게시글 URL
	private String boardDataTitle; // 게시글 제목
	private String token; // 토큰
	
	public FCMDTO() {}
	
	public FCMDTO(String boardTitle, String boardDataUrl, String boardDataTitle, String token) {
		this.boardTitle = boardTitle;
		this.boardDataUrl = boardDataUrl;
		this.boardDataTitle = boardDataTitle;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardDataUrl() {
		return boardDataUrl;
	}

	public void setBoardDataUrl(String boardDataUrl) {
		this.boardDataUrl = boardDataUrl;
	}

	public String getBoardDataTitle() {
		return boardDataTitle;
	}

	public void setBoardDataTitle(String boardDataTitle) {
		this.boardDataTitle = boardDataTitle;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
