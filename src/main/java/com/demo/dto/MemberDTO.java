package com.demo.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	
	@NotNull(groups = {ValidationGroups.modifyValidtion.class}, message = "Please enter member's id")
	private Long id;
	
	@NotNull(groups = {ValidationGroups.createValidation.class}, message = "Please enter member's team ID.")
	private Long teamid;
	
	@NotNull(groups = {ValidationGroups.createValidation.class}, message = "Please enter member's name.")
	private String name;
	
	@NotNull(groups = {ValidationGroups.createValidation.class}, message = "Please enter member's age.")
	private	Integer age;
	
	@NotNull(groups = {ValidationGroups.createValidation.class}, message = "Please enter member's gender.")
	@Pattern(groups = {ValidationGroups.createValidation.class, ValidationGroups.modifyValidtion.class}, regexp = "[MWmw]", message = "Please enter gender as M or W")
	private String gender;
	
}