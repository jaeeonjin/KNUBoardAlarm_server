package com.jinjaeeon.boardsystem.api.board.dto;

import java.util.Map;

public class BoardDTO {
	private String id; // 사용자 ID
	private String name; // 게시판 이름
	private String uri; // 게시판 주소
	private String type; // 게시판 종류
	private Map<String, String> data; // 게시판 데이터

	public BoardDTO(String id, String uri) {
		this.id = id;
		this.uri = uri;
	}

	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return " 요청자 : " + id + "\n 게시판 이름 : " + name + "\n 게시판 주소 : " + uri + "\n 게시판 종류 : " + type;
	}
}
