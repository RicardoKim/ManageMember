package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody MemberDTO dto){
		log.info("Create Member Start");
		log.info("request validation check");
		dtoValidationCheck(dto);
		log.info("request validated");
		TeamEntity searchedTeam = teamService.searchWithId(dto.getTeamid());
		log.info("Get Team Entity from team id");
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
		
	
	}
	
	@GetMapping()
	public ResponseEntity<?> search(@RequestParam(required = false) String name, String age, String team, String gender){
	
		List<MemberEntity> searchedOutput = null;
		if(name != null) {
			log.info("name search start");
			searchedOutput = memberService.searchWithCondition("name", name);
		}
		else if(age != null) {
			log.info("age search start");
			searchedOutput = memberService.searchWithCondition("age", age);
		}
		else if(team != null) {
			log.info("team search start");
			searchedOutput = memberService.searchWithCondition("team", team);
		}
		else if(gender != null) {
			log.info("gender search start");
			searchedOutput = memberService.searchWithCondition("gender", gender);
		}
		else {
			log.info("total search start");
			searchedOutput = memberService.totalSearch();
		}
	
		log.info("found a member who fits the search conditions.");
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.info("Http : 200 \n");
		return ResponseEntity.ok().body(response);

	}
	

	@GetMapping("/{id}")
	public ResponseEntity<?> idSearch(@PathVariable("id") Long id){
		MemberEntity searchedOutput = memberService.searchWithId(id);
		log.info("found a member with id.");
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.info("Http : 200 \n");
		return ResponseEntity.ok().body(response);
	}
	
	
	
	
	@PutMapping()
	public ResponseEntity<?> modifyInfo(@RequestBody Map<String, String> Parameters){
		log.info("Modify Information start");
		modifyInfoRequestValidationCheck(Parameters);
		log.info("Request is validated");
		String MemberId = Parameters.get("id").toString();
		Parameters.remove("id");
		MemberEntity modifiedMember = memberService.modifyInfo(MemberId, Parameters);
		log.info("Modified is succeed");
		List<MemberEntity> modifiedMemberInfo = new ArrayList<MemberEntity>();
		modifiedMemberInfo.add(modifiedMember);
		List<MemberDTO> responseOutput = memberService.entityToDTO(modifiedMemberInfo);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.info("Http : 200 \n");
		return ResponseEntity.ok().body(response);
	}
	
	@ExceptionHandler(RuntimeException.class)
	private ResponseEntity<?> handlingRuntimeException(Exception e){
		ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(e.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<?> handlingNullPointerException(Exception e){
		ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).error(e.getMessage()).build();
		return ResponseEntity.ok().body(errorResponse);
	}
	
	private void dtoValidationCheck(final MemberDTO entity) {
		System.out.println(entity);
		if(entity.getName() == null || entity.getAge() == null || entity.getGender() == null || entity.getTeamid() == null) {
			throw new RuntimeException("Invalid Information.");
		}
		if("M".equals(entity.getGender()) && "W".equals(entity.getGender())) {
			System.out.println(entity.getGender());
			System.out.println("M".equals(entity.getGender()));
			throw new RuntimeException("Invalid Gender.");
		}
	}
	
	private void modifyInfoRequestValidationCheck(Map<String, String> parameters) {
		Set<String> keyset = parameters.keySet();
		if(keyset.contains("id")){
			
		}else {
			throw new RuntimeException("Invalid Option");
		}
		if(keyset.contains("gender")) {
			String gender = parameters.get("gender");
			if(!gender.equals("W") && !gender.equals("M")) {
				throw new RuntimeException("Invalid gender");
			}
		}
	}
	

}
