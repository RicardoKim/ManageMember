package com.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.demo.dto.ValidationGroups;
import com.demo.dto.ValidationGroups.modifyValidtion;
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
	public ResponseEntity<?> create(@RequestBody @Validated(ValidationGroups.createValidation.class) MemberDTO dto){
		log.debug("Create Member Start");
		log.debug("New Member Info : " + dto.toString());
		TeamEntity searchedTeam = teamService.searchWithId(dto.getTeamid());
		log.debug("Get Team Entity from team id");
		final MemberEntity responseMemberEntity = MemberEntity.builder()
				.name(dto.getName())
				.age(dto.getAge())
				.teamId(searchedTeam.getId())
				.gender(dto.getGender())
				.build();
		log.debug("Member Entity is created");
		memberService.create(responseMemberEntity);
		log.debug("Member Info is inserted in DB");
		List<MemberDTO> createdMemberDTO = memberService.entityToDTO(new ArrayList<>(Arrays.asList(responseMemberEntity)));
		log.debug("Created Member Entity : " + createdMemberDTO.toString());
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(createdMemberDTO).build();
		log.debug("Response : " + response.toString() + "\n");
		return ResponseEntity.ok().body(response);

	}
	

	@GetMapping()
	public ResponseEntity<?> search(@RequestParam Map<String, String> Parameters){
		log.debug("Searching Condition : " + Parameters.toString());
		List<MemberEntity> searchedOutput = memberService.search(Parameters);
		log.debug("Searched Member Entity" + searchedOutput.toString());
		ResponseDTO<MemberEntity> response = ResponseDTO.<MemberEntity>builder().statusCode(200).data(searchedOutput).build();
		log.debug("Response : " + response.toString()+ "\n");
		return ResponseEntity.ok().body(response);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<?> idSearch(@PathVariable("id") Long id){
		log.debug("Search Member with ID \n Request ID : " + id.toString());
		MemberEntity searchedOutput = memberService.searchWithId(id);
		log.debug("Searched Member : " + searchedOutput.toString());
		List<MemberDTO> responseOutput = memberService.entityToDTO(searchedOutput);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.debug("Response : " + response.toString()+ "\n");
		return ResponseEntity.ok().body(response);
	}
	
	
	@PutMapping()
	public ResponseEntity<?> modifyInfo(@RequestBody @Validated(ValidationGroups.modifyValidtion.class) MemberDTO dto){
		log.debug("Modify Information start");
		String MemberId = dto.getId().toString();
		MemberEntity modifiedMember = memberService.modifyInfo(MemberId, dto);
		log.debug("Modified is succeed");
		List<MemberEntity> modifiedMemberInfo = new ArrayList<MemberEntity>();
		modifiedMemberInfo.add(modifiedMember);
		log.debug("Modfied Member Info : " + modifiedMemberInfo.toString());
		List<MemberDTO> responseOutput = memberService.entityToDTO(modifiedMemberInfo);
		ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().statusCode(200).data(responseOutput).build();
		log.debug("Response : " + response.toString()+ "\n");
		return ResponseEntity.ok().body(response);
	}

}
