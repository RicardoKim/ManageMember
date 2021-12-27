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
	private TeamService service;
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TeamDTO dto){
		final TeamEntity responseTeamDTO = TeamEntity.builder().name(dto.getName()).build();
		try {
			service.create(responseTeamDTO);
			return ResponseEntity.ok().body(responseTeamDTO);
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/totalsearch")
	public ResponseEntity<?> totalSearch(){
		List<TeamEntity> searchedOutput = service.totalSearch();
		return ResponseEntity.ok().body(searchedOutput);
	}
	
	@GetMapping("/selectsearch")
	public ResponseEntity<?> selectSearch(@RequestBody Map<String, Object> allParameters){
		if(allParameters.isEmpty()) {
			return ResponseEntity.badRequest().body("Empty Request");
		}
		else {
			for (String key : allParameters.keySet()) {
				String targetValue = (String) allParameters.get(key);
				TeamEntity searchedOutput = service.selectSearch(key, targetValue);
				return ResponseEntity.ok().body(searchedOutput);
			}
		}
		return null;
	}
	
	@GetMapping("/membersearch")
	public ResponseEntity<?> memberSearch(@RequestBody Map<String, Object> allParameters){
		if(allParameters.isEmpty()) {
			return ResponseEntity.badRequest().body("Empty Request");
		}
		else {
			for (String key : allParameters.keySet()) {
				String targetValue = (String) allParameters.get(key);
				TeamEntity findedTeam = service.ExtractTeamEntityFromName(targetValue);
				Long targetId = findedTeam.getId();
				List<MemberEntity> searchedOutput = memberService.selectSearch("team_name", targetId.toString());
				return ResponseEntity.ok().body(searchedOutput);
			}
		}
		return null;
	}
}
