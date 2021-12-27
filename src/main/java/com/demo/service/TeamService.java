package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.demo.model.TeamEntity;
import com.demo.persistence.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepository;
	
	public void create(final TeamEntity entity) {
		validate(entity);
		teamRepository.save(entity);
	}
	
	public List<TeamEntity> totalSearch(){
		List<TeamEntity> searchedOutput = teamRepository.findAll();
		return searchedOutput;
	}
	
	public TeamEntity selectSearch(String key, String targetValue){
		TeamEntity searchedOutput = null;
		if(key.equals("id")) {
			searchedOutput = teamRepository.findById(Long.parseLong(targetValue));
			
		}
		else {
			searchedOutput = teamRepository.findByName(targetValue);
		}
		
		return searchedOutput;
	}
	
	public TeamEntity ExtractTeamEntityFromName(final String teamName){
		System.out.println("Extract");
		System.out.println(teamName);
		TeamEntity searchOutput = (TeamEntity) teamRepository.findByName(teamName);
	
		if(searchOutput == null) {
			throw new RuntimeException("Requested Team is not existed.");
		}

		return searchOutput;
	}
	
	
	private void validate(final TeamEntity entity) {
		if(entity.getName() == null) {
			throw new RuntimeException("Invalid Name.");
		}
		TeamEntity searchOutput = teamRepository.findByName(entity.getName());
		if(searchOutput != null) {
			throw new RuntimeException("Team name is already exist.");
		}
		
	}
}
