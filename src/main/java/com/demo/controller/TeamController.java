package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

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
	public ResponseEntity<?> create(@RequestBody @Valid TeamDTO dto){
		log.debug("team create");
		System.out.println(dto);
		TeamEntity requestTeamEntity = TeamEntity.builder().name(dto.getName()).build();
		System.out.println(requestTeamEntity);
		teamService.create(requestTeamEntity);
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(requestTeamEntity))).build();
		log.debug("HTTP : 200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping()
	public ResponseEntity<?> totalSearch(){
		log.debug("View all team information");
		List<TeamEntity> searchedOutput = teamService.totalSearch();
		log.debug("result received from team service layer");
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(searchedOutput).build();
		log.debug("HTTP : 200 \n \n");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> searchWithId(@PathVariable("id") Long id){
		log.debug("id search");
		TeamEntity searchedTeam = teamService.searchWithId(id);
		ResponseDTO<TeamEntity> response = ResponseDTO.<TeamEntity>builder().statusCode(200).data(new ArrayList<>(Arrays.asList(searchedTeam))).build();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/{id}/members")
	public ResponseEntity<?> memberSearch(@PathVariable("id") Long id){
		log.debug("member search");
		TeamEntity searchedTeam = teamService.searchWithId(id);
		List<MemberEntity> searchedOutput = memberService.searchWithCondition("team", searchedTeam.getId().toString());
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.debug("200 \n \n");
		return ResponseEntity.ok().body(response);
	}

}
