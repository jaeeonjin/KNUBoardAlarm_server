package com.jinjaeeon.boardsystem.api.member.dto;

public class MemberDTO {
	private String id;
	private String name;
	private String token;
	
	public MemberDTO() {}
	
	public MemberDTO(String id, String name, String token) {
		this.setId(id);
		this.setName(name);
		this.setToken(token);
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "{ " + id + " " + name + " " + token + "} ";
	}
	
}
