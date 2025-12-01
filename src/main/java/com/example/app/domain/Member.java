package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Member {
	
	private Integer id;
	
	private String name;
	
	@NotBlank
	private String loginId;
	
	@NotBlank
	private String loginPass;

}
