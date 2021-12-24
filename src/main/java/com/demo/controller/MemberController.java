package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.MemberDTO;
import com.demo.dto.ResponseDTO;
import com.demo.model.MemberEntity;
import com.demo.model.TeamEntity;
import com.demo.service.MemberService;
import com.demo.service.TeamService;

@RestController
@RequestMapping("members")
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private TeamService teamService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody MemberDTO dto){
		
		try {
			String targetTeamName = dto.getTeamName();
			
			List<TeamEntity> findedTeamByName = teamService.ExtractTeamEntityFromName(targetTeamName);
			TeamEntity team = findedTeamByName.get(0);
			
			final MemberEntity responseMemberDTO = MemberEntity.builder()
					.name(dto.getName())
					.age(dto.getAge())
					.team(team)
					.gender(dto.getGender())
					.build();
			service.create(responseMemberDTO);
			return ResponseEntity.ok().body(responseMemberDTO);
			
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	
	}
}
