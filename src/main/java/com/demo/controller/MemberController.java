package com.demo.controller;

import java.util.List;
import java.util.Map;

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
			
			TeamEntity findedTeam = teamService.ExtractTeamEntityFromName(targetTeamName);

			
			final MemberEntity responseMemberDTO = MemberEntity.builder()
					.name(dto.getName())
					.age(dto.getAge())
					.teamId(findedTeam.getId())
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
	
	@PutMapping("/modifyinfo")
	public ResponseEntity<?> modifyInfo(@RequestBody Map<String, Object> allParameters){
		if(allParameters.isEmpty()) {
			return ResponseEntity.badRequest().body("Empty Request");
		}
		else {
			try {
			
				String MemberId = (String) allParameters.get("id");
			
				String Info = (String) allParameters.get("info");
				String value = (String) allParameters.get("value");
			
				MemberEntity modifiedMember = service.modifyInfo(MemberId, Info, value);
			
				return ResponseEntity.ok().body( modifiedMember);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e);
			}
		}
	
		
	}
	
	@GetMapping("/totalsearch")
	public ResponseEntity<?> totalSearch(){
		List<MemberEntity> searchedOutput = service.totalSearch();
		List<MemberDTO> responseOutput = service.entityToDTO(searchedOutput);
		return ResponseEntity.ok().body(responseOutput);
	}
	
	@GetMapping("/selectsearch")
	public ResponseEntity<?> selectSearch(@RequestBody Map<String, Object> allParameters){
		if(allParameters.isEmpty()) {
			return ResponseEntity.badRequest().body("Empty Request");
		}
		else {
			try {
				List<MemberEntity> searchedOutput = null;
				for (String key : allParameters.keySet()) {
					String value = (String) allParameters.get(key);
					if(key == "team_name") {
						TeamEntity findedTeam = teamService.ExtractTeamEntityFromName(value);
						Long targetId = findedTeam.getId();
						searchedOutput = service.selectSearch(key, targetId.toString());
						
					}
					else {
						searchedOutput = service.selectSearch(key, value);
						
					}
					
				}
				List<MemberDTO> responseOutput = service.entityToDTO(searchedOutput);
				return ResponseEntity.ok().body(responseOutput);
				
			}catch(Exception e) {
				return ResponseEntity.ok().body("There's no member who meets the requirements.");
			}
			
		}

	}
}
