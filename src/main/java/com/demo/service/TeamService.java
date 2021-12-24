package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.TeamEntity;
import com.demo.persistence.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 자바에서 자동적으로 로그를 만들어주는 라이브러리이다.
@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepository;
	
	public void create(final TeamEntity entity) {
		validate(entity);
		teamRepository.save(entity);
	}
	
	public List<TeamEntity> ExtractTeamEntityFromName(final String teamName){
		List<TeamEntity> searchOutput = teamRepository.findByName(teamName);
		if(searchOutput.isEmpty()) {
			throw new RuntimeException("Requested Team is not existed.");
		}
		return searchOutput;
	}
	
	private void validate(final TeamEntity entity) {
		if(entity.getName() == null) {
			throw new RuntimeException("Invalid Name.");
		}
		List<TeamEntity> searchOutput = teamRepository.findByName(entity.getName());
		if(!searchOutput.isEmpty()) {
			throw new RuntimeException("Team name is already exist.");
		}
		
	}
}
