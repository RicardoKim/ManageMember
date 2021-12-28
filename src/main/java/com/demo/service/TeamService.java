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
	@Autowired
	private LogService logService;
	
	public void create(final TeamEntity entity) {
		log.info("Team Created");
		validate(entity);
		teamRepository.save(entity);
	}
	
	public List<TeamEntity> totalSearch(){
		log.info("total search from DB");
		List<TeamEntity> searchedOutput = teamRepository.findAll();
		return searchedOutput;
	}
	
	public TeamEntity selectSearch(String key, String value){
		log.info("select search");
		TeamEntity searchedOutput = null;
		
		if(key.equals("id")) {
			log.info("search with ID");
			searchedOutput = teamRepository.findById(Long.parseLong(value));
			
		}
		else if(key.equals("team_name")){
			log.info("search with team name");
			searchedOutput = teamRepository.findByName(value);
		}
		
		if(searchedOutput == null) {
			throw new NullPointerException("We can't find the team that meets requirements");
		}
		return searchedOutput;
	}
	
	
	private void validate(final TeamEntity entity) {
		if(entity.getName() == null) {
			log.info("Invalid Name");
			throw new RuntimeException("Invalid Name.");
		}
		TeamEntity searchOutput = teamRepository.findByName(entity.getName());
		if(searchOutput != null) {
			log.info("Exist Team");
			throw new RuntimeException("Team name is already exist.");
		}
		
	}
}
