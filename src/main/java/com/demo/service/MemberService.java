package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.MemberDTO;
import com.demo.model.MemberEntity;
import com.demo.persistence.CustomSQLQuery;
import com.demo.persistence.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CustomSQLQuery customQuery;
	
	public void create(final MemberEntity entity) {
		log.debug("create member");
		memberRepository.save(entity);
	}
	
	public List<MemberEntity> totalSearch(){
		log.debug("total search");
		List<MemberEntity> searchedOutput = memberRepository.findAll();
		return searchedOutput;
	}
	
	public MemberEntity searchWithId(Long id){
		log.debug("select search");
		MemberEntity searchedOutput = memberRepository.findById(id);
		
		if(searchedOutput == null) {
			throw new NullPointerException("We can't find the member that meets requirements");
		}
		
		return searchedOutput;
	}
	
	public List<MemberEntity> search(Map<String, String> Parameters) {
		if(Parameters.isEmpty()) {
			List<MemberEntity> searchedOutput = memberRepository.findAll();
			return searchedOutput;
		}
		for(String key : Parameters.keySet()) {
			String value = Parameters.get(key);
			List<MemberEntity> searchedMember = customQuery.customSQLSearchWithCondition(key, value);
			if(searchedMember.isEmpty()) {
				throw new NullPointerException("We can't find the member that meets requirements");
			}
			return searchedMember;
		}
		
		return null;
	}
	
	public List<MemberEntity> searchWithCondition(String key, String value){
		log.debug("select search");
		List<MemberEntity> searchedOutput = null;
		if(key == "name") {
			log.debug("search member with name");
			searchedOutput = memberRepository.findByName(value);
		}
		else if(key == "team") {
			log.debug("search member with team name");
			searchedOutput = memberRepository.findByTeamId(Long.parseLong(value));
		}
		else if(key == "age") {
			log.debug("search member with age");
			searchedOutput = memberRepository.findByAge(Integer.parseInt(value));
		}
		else if(key == "gender") {
			log.debug("search member with gender");
			searchedOutput = memberRepository.findByGender(value);
		}
		else {
			log.debug("User Invalid Option");
			throw new RuntimeException("Invalid Option");
		}
		
		if(searchedOutput.isEmpty()) {
			throw new NullPointerException("We can't find the member that meets requirements");
		}
		
		return searchedOutput;
	}
	
	public List<MemberDTO> entityToDTO(List<MemberEntity> request){
		List<MemberDTO> dtoList = new ArrayList<MemberDTO>();
		for(MemberEntity entity : request) {
			Long teamId = entity.getTeamId();

			dtoList.add(MemberDTO.builder().id(entity.getId()).age(entity.getAge()).name(entity.getName()).gender(entity.getGender()).teamid(teamId).build());
		}
		return dtoList;
		
	}
	
	public List<MemberDTO> entityToDTO(MemberEntity entity){
		List<MemberDTO> dtoList = new ArrayList<MemberDTO>();
		Long teamId = entity.getTeamId();
		dtoList.add(MemberDTO.builder().id(entity.getId()).age(entity.getAge()).name(entity.getName()).gender(entity.getGender()).teamid(teamId).build());
		return dtoList;
		
	}
	
	public MemberEntity modifyInfo(String Id, MemberDTO dto) {
		Long LongId = Long.valueOf(Id);
		log.debug("modify info");
		MemberEntity searchedOutput = memberRepository.findById(LongId);
		if(searchedOutput == null) {
			throw new NullPointerException("We can't find the member that meets requirements.");
		}
		if(dto.getName() != null) {
			log.debug("change name");
			searchedOutput.setName(dto.getName());
		}
		else if(dto.getTeamid() != null){
			log.debug("change team");
			searchedOutput.setTeamId(dto.getTeamid());
		}
		else if(dto.getAge() != null) {
			log.debug("change age");
			searchedOutput.setAge(dto.getAge());
		}
		else if(dto.getGender() != null) {
			log.debug("change gender");
			searchedOutput.setGender(dto.getGender());
		}
		
		memberRepository.save(searchedOutput);
		return searchedOutput;
		
	}
	
	
	
}