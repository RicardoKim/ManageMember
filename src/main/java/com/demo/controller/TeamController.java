package com.demo.controller;

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
import com.demo.service.MemberService;
import com.demo.service.TeamService;

@RestController
@RequestMapping("teams")
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TeamDTO dto){
		TeamEntity requestTeamEntity = TeamEntity.builder().name(dto.getName()).build();
		try {
			teamService.create(requestTeamEntity);
			return ResponseEntity.ok().body(requestTeamEntity);
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TeamDTO> errorResponse = ResponseDTO.<TeamDTO>builder().statusCode(400).error(error).build();
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}
	
	@GetMapping("/totalsearch")
	public ResponseEntity<?> totalSearch(){
		List<TeamEntity> searchedOutput = teamService.totalSearch();
		return ResponseEntity.ok().body(searchedOutput);
	}
	
	@GetMapping("/selectsearch")
	public ResponseEntity<?> selectSearch(@RequestBody Map<String, Object> Parameters){
		try {
			requestValidationCheck(Parameters);
			String key = getKeyFromFirstIndexOfHashMap(Parameters);
			String value = getValueFromFirstIndexOfHashMap(Parameters);
			TeamEntity searchedTeam = teamService.selectSearch(key, value);
			return ResponseEntity.ok().body(searchedTeam);
		}catch(Exception e) {
			String errorMessage = e.toString();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(errorMessage).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@GetMapping("/membersearch")
	public ResponseEntity<?> memberSearch(@RequestBody Map<String, Object> Parameters){
		try {
			
			requestValidationCheck(Parameters);
			String value = getValueFromFirstIndexOfHashMap(Parameters);
			TeamEntity searchedTeam = teamService.selectSearch("team_name", value);
			List<MemberEntity> searchedOutput = memberService.selectSearch("team_name", searchedTeam.getId().toString());
			List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
			return ResponseEntity.ok().body(responseOutput);
			
		}catch(Exception e) {
			String errorMessage = e.toString();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(errorMessage).build();
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
