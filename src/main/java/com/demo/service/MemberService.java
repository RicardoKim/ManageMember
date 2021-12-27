package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.MemberDTO;
import com.demo.model.MemberEntity;
import com.demo.model.TeamEntity;
import com.demo.persistence.MemberRepository;
import com.demo.persistence.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	public void create(final MemberEntity entity) {
		validate(entity);
		memberRepository.save(entity);
	}
	
	public List<MemberEntity> totalSearch(){
		List<MemberEntity> searchedOutput = memberRepository.findAll();
		return searchedOutput;
	}
	
	public List<MemberEntity> selectSearch(String key, String value){
	
		List<MemberEntity> searchedOutput = null;
		if(key == "name") {
			searchedOutput = memberRepository.findByName(value);
		}
		else if(key == "team_name") {
			searchedOutput = memberRepository.findByTeamId(Long.parseLong(value));
		}
		else if(key == "age") {
			searchedOutput = memberRepository.findByAge(Integer.parseInt(value));
		}
		else if(key == "gender") {
			searchedOutput = memberRepository.findByGender(value);
		}
		
		
		return searchedOutput;
	}
	
	public List<MemberDTO> entityToDTO(List<MemberEntity> request){
		List<MemberDTO> dtoList = new ArrayList<MemberDTO>();
		for(MemberEntity entity : request) {
			Long teamId = entity.getTeamId();
			String teamName = teamRepository.findById(teamId).getName();
			
			dtoList.add(MemberDTO.builder().id(entity.getId()).age(entity.getAge()).name(entity.getName()).gender(entity.getGender()).teamName(teamName).build());
		}
		System.out.println(dtoList);
		return dtoList;
		
	}
	
	public MemberEntity modifyInfo(String Id, String key, String value) {
		Long LongId = Long.valueOf(Id);
		try {
			MemberEntity searchedOutput = memberRepository.findById(LongId);

			if(key.equals("name")) {
			
				searchedOutput.setName(value);
			}
			else if(key.equals("team_name") ){
				searchedOutput.setTeamId(Long.parseLong(value));
			}
			else if(key.equals("age")) {
			
				searchedOutput.setAge(Integer.parseInt(value));
			}
			else if(key.equals("gender")) {
		
				searchedOutput.setGender(value);
			}
		
			memberRepository.save(searchedOutput);
			return searchedOutput;
		}catch(Exception e) {
			throw new RuntimeException("Request Member doesn't exist.");
		}
		
	}
	
	private void validate(final MemberEntity entity) {
		if(entity.getName() == null || entity.getAge() == null || entity.getGender() == null) {
			throw new RuntimeException("Invalid Information.");
		}
		if("M".equals(entity.getGender()) && "W".equals(entity.getGender())) {
			System.out.println(entity.getGender());
			System.out.println("M".equals(entity.getGender()));
			throw new RuntimeException("Invalid Gender.");
		}
	}
}