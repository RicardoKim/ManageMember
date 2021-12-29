package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("teams")
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private MemberService memberService;
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody TeamDTO dto){
		log.info("team create");
		System.out.println(dto);
		TeamEntity requestTeamEntity = TeamEntity.builder().name(dto.getName()).build();
		System.out.println(requestTeamEntity);
		teamService.create(requestTeamEntity);
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(requestTeamEntity))).build();
		log.info("HTTP : 200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping()
	public ResponseEntity<?> totalSearch(){
		log.info("View all team information");
		List<TeamEntity> searchedOutput = teamService.totalSearch();
		log.info("result received from team service layer");
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(searchedOutput).build();
		log.info("HTTP : 200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> searchWithId(@PathVariable("id") Long id){
		log.info("id search");
		TeamEntity searchedTeam = teamService.searchWithId(id);
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(searchedTeam))).build();
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping("/{id}/members")
	public ResponseEntity<?> memberSearch(@PathVariable("id") Long id){
		log.info("member search");
		TeamEntity searchedTeam = teamService.searchWithId(id);
		List<MemberEntity> searchedOutput = memberService.searchWithCondition("team", searchedTeam.getId().toString());
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.info("200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@ExceptionHandler(RuntimeException.class)
	private ResponseEntity<?> handlingRuntimeException(Exception e){
		ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(e.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<?> handlingNullPointerException(Exception e){
		ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).build();
		return ResponseEntity.ok().body(errorResponse);
	}
}
