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
public class TeamDTO {
	private Integer id;
	private String name;
	
	public TeamDTO(final TeamEntity entity) {
		this.name = entity.getName();
		
	}
}
