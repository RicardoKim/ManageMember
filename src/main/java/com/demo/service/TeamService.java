package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		log.info("Team Created");
		isNameDuplicated(entity);
		teamRepository.save(entity);
	}
	
	public List<TeamEntity> totalSearch(){
		log.info("total search from DB");
		List<TeamEntity> searchedOutput = teamRepository.findAll();
		return searchedOutput;
	}
	
	public TeamEntity searchWithId(Long id){
		log.info("search with ID");
		TeamEntity searchedResult = teamRepository.findById(id);
		
		if(searchedResult == null) {
			throw new NullPointerException("We can't find the team that meets requirements");
		}
		return searchedResult;
	}
	
	private void isNameDuplicated(final TeamEntity entity) {
		TeamEntity searchOutput = teamRepository.findByName(entity.getName());
		if(searchOutput != null) {
			log.info("Exist Team");
			throw new RuntimeException("Team name is already exist.");
		}
		
	}
}
