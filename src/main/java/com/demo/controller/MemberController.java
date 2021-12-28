package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamService teamService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody MemberDTO dto){
		log.info("Create Member Start");
		try {
			log.info("request validation check");
			dtoValidationCheck(dto);
			log.info("request validated");
			String targetTeamName = dto.getTeamName();
			
			TeamEntity searchedTeam = teamService.selectSearch("team_name", targetTeamName);
			log.info("Get Team Entity from team name");
			
			final MemberEntity responseMemberEntity = MemberEntity.builder()
					.name(dto.getName())
					.age(dto.getAge())
					.teamId(searchedTeam.getId())
					.gender(dto.getGender())
					.build();
			log.info("Member Entity is created");
			memberService.create(responseMemberEntity);
			log.info("Member Info is inserted in DB");
			List<MemberDTO> createdMemberDTO = memberService.entityToDTO(new ArrayList<>(Arrays.asList(responseMemberEntity)));
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(createdMemberDTO).build();
			log.info("HTTP 200 \n");
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(400).error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	
	}
	
	
	
	@GetMapping("/totalsearch")
	public ResponseEntity<?> totalSearch(){
		log.info("total search start");
		List<MemberEntity> searchedOutput = memberService.totalSearch();
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.info("Http : 200 \n ");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/selectsearch")
	public ResponseEntity<?> selectSearch(@RequestBody Map<String, Object> Parameters){
		try {
			log.info("select start is started");
			requestValidationCheck(Parameters);
			log.info("request is validated");
			List<MemberEntity> searchedOutput = null;
			String key = getKeyFromFirstIndexOfHashMap(Parameters);
			String value = getValueFromFirstIndexOfHashMap(Parameters);
			
			if(key == "team_name") {
				TeamEntity searchedTeam = teamService.selectSearch("team_name", value);
				searchedOutput = memberService.selectSearch(key, searchedTeam.getId().toString());
			}else {
				searchedOutput = memberService.selectSearch(key, value);
			}
			log.info("found a member who fits the search conditions.");
			List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
			log.info("Http : 200 \n");
			return ResponseEntity.ok().body(response);
		}catch(NullPointerException e) {
			ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).build();
			return ResponseEntity.ok().body(errorResponse);
		}catch(RuntimeException e) {
			String errorMessage = e.getMessage();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(errorMessage).build();
			return ResponseEntity.badRequest().body(response);
		}

	}
	
	@PutMapping("/modifyinfo")
	public ResponseEntity<?> modifyInfo(@RequestBody Map<String, Object> Parameters){
		try {
			log.info("Modify Information start");
			modifyInfoRequestValidationCheck(Parameters);
			log.info("Request is validated");
			String MemberId = (String) Parameters.get("id");
			String Info = (String) Parameters.get("info");
			String value = (String) Parameters.get("value");
			if(Info.equals("team_name")) {
				TeamEntity searchedTeam = teamService.selectSearch("team_name", value);
				value = searchedTeam.getId().toString();
			}
			
			MemberEntity modifiedMember = memberService.modifyInfo(MemberId, Info, value);
			log.info("Modified is succeed");
			List<MemberEntity> modifiedMemberInfo = new ArrayList<MemberEntity>();
			modifiedMemberInfo.add(modifiedMember);
			List<MemberDTO> responseOutput = memberService.entityToDTO(modifiedMemberInfo);
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
			log.info("Http : 200 \n");
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(e.getMessage()).build();
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
			
		}else {
			String key = getKeyFromFirstIndexOfHashMap(requestParameters);
			if(!key.equals("team_name") && !key.equals("id")) {
				throw new RuntimeException("Invalid Search Option");
			}
		}
	}
	
	private void modifyInfoRequestValidationCheck(Map<String, Object> requestParameters) {
		Set<String> keyset = requestParameters.keySet();
		if(keyset.contains("id") && keyset.contains("info") && keyset.contains("value")){
			
		}else {
			throw new RuntimeException("Invalid Option");
		}
	}
	
	private void dtoValidationCheck(MemberDTO dto) {
		if(!dto.getGender().equals("M") && !dto.getGender().equals("W")) {
			throw new RuntimeException("Invalid Gender");
		}
	}
}
