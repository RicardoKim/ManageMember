package com.demo.dto;

import com.demo.model.TeamEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

	private Long id;
	private String teamName;
	private String name;
	private	Integer age;
	private String gender;
	
}