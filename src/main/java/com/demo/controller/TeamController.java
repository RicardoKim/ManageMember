package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.MemberDTO;
import com.demo.dto.ResponseDTO;
import com.demo.dto.TeamDTO;
import com.demo.model.MemberEntity;
import com.demo.model.TeamEntity;
import com.demo.service.LogService;
import com.demo.service.MemberService;
import com.demo.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("teams")
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private LogService logService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TeamDTO dto){
		log.info("create");
		TeamEntity requestTeamEntity = TeamEntity.builder().name(dto.getName()).build();
		try {
			teamService.create(requestTeamEntity);
			ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(requestTeamEntity))).build();
			log.info("HTTP : 200 \n \n");
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			log.info(e.getMessage());
			String error = e.getMessage();
			ResponseDTO<TeamDTO> errorResponse = ResponseDTO.<TeamDTO>builder().statusCode(400).error(error).build();
			log.info("HTTP : 400 \n \n");
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}
	
	@GetMapping("/totalsearch")
	public ResponseEntity<?> totalSearch(){
		log.info("total search start");
		List<TeamEntity> searchedOutput = teamService.totalSearch();
		log.info("total search result received from team service layer");
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(searchedOutput).build();
		log.info("HTTP : 200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/selectsearch")
	public ResponseEntity<?> selectSearch(@RequestBody Map<String, Object> Parameters){
		log.info("select search start");
		try {
			requestValidationCheck(Parameters);
			log.info("request validated ");
			String key = getKeyFromFirstIndexOfHashMap(Parameters);
			String value = getValueFromFirstIndexOfHashMap(Parameters);
			TeamEntity searchedTeam = teamService.selectSearch(key, value);
			ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(searchedTeam))).build();
			log.info("HTTP : 200 \n \n");
			return ResponseEntity.ok().body(response);
		}catch(NullPointerException e) {
			log.info(e.getMessage());
			ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).build();
			log.info("HTTP : 204 \n \n");
			return ResponseEntity.ok().body(errorResponse);
		}catch(RuntimeException e) {
			log.info(e.getMessage());
			String errorMessage = e.getMessage();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(errorMessage).build();
			log.info("HTTP : 400 \n \n");
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@GetMapping("/membersearch")
	public ResponseEntity<?> memberSearch(@RequestBody Map<String, Object> Parameters){
		log.info("member search start");
		try {
			
			requestValidationCheck(Parameters);
			log.info("request validated");
			String value = getValueFromFirstIndexOfHashMap(Parameters);
			TeamEntity searchedTeam = teamService.selectSearch("team_name", value);
			List<MemberEntity> searchedOutput = memberService.selectSearch("team_name", searchedTeam.getId().toString());
			List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
			log.info("200 \n \n");
			return ResponseEntity.ok().body(response);
			
		}catch(NullPointerException e) {
			log.info(e.getMessage());
			ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).build();
			log.info("204 \n \n");
			return ResponseEntity.ok().body(errorResponse);
		}catch(RuntimeException e) {
			log.info(e.getMessage());
			String errorMessage = e.getMessage();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(errorMessage).build();
			log.info("400 \n \n");
			return ResponseEntity.badRequest().body(response);
		}
		
		
		
		
		
	}
	
	private String getKeyFromFirstIndexOfHashMap(Map<String, Object> Parameter) {
		String key = (String) Parameter.keySet().toArray()[0];
		return key;
	}
	
	private String getValueFromFirstIndexOfHashMap(Map<String, Object> Parameter) {
		String key = getKeyFromFirstIndexOfHashMap(Parameter);
		String value = (String) Parameter.get(key);
		return value;
	}
	
	private void requestValidationCheck(Map<String, Object> requestParameters) {
		if(requestParameters.isEmpty()) {
			throw new RuntimeException("Empty Request");
		}
		if(requestParameters.keySet().toArray().length > 2) {
			throw new RuntimeException("There are more than two options requested");
			
		}
		else {
			String key = getKeyFromFirstIndexOfHashMap(requestParameters);
			if(!key.equals("team_name") && !key.equals("id")) {
				throw new RuntimeException("Invalid Search Option");
			}
		}
	}
}
