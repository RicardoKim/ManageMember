package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.MemberEntity;
import com.demo.persistence.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 자바에서 자동적으로 로그를 만들어주는 라이브러리이다.
@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	public void create(final MemberEntity entity) {
		validate(entity);
		memberRepository.save(entity);
	}
	
	private void validate(final MemberEntity entity) {
		if(entity.getName() == null || entity.getAge() == null || entity.getGender() == null) {
			throw new RuntimeException("Invalid Information.");
		}
		if(entity.getGender() != "M" && entity.getGender() != "W") {
			throw new RuntimeException("Invalid Information.");
		}
		
		List<MemberEntity> searchOutput = memberRepository.findByName(entity.getName());
		if(!searchOutput.isEmpty()) {
			throw new RuntimeException("Team name is already exist.");
		}
		
	}
}