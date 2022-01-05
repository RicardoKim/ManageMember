package com.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.Status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ServiceTest {
	
	private static String TEAM_TEST_URI = "/teams";
	private static String MEMBERS_TEST_URI = "/members";
	
	@Autowired
	private MockMvc mvc;

	@Test
	@Transactional
	void findTeamTest() throws Exception {
		mvc.perform(get(TEAM_TEST_URI)
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional
	void findMemberWithCondtionTest() throws Exception {
		
		mvc.perform(get(MEMBERS_TEST_URI + "/1")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
		
		mvc.perform(get(MEMBERS_TEST_URI + "?name=member1")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	
		
		
		mvc.perform(get(MEMBERS_TEST_URI + "?team_id=1")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	@Transactional
	void modifyUserInfo() throws Exception {
		Map<String, Object> w = new HashMap<>();
	    w.put("id", 19);
	    w.put("name", "member101");
	    w.put("gender", "W");
	    String json = new ObjectMapper().writeValueAsString(w);
		mvc.perform(put(MEMBERS_TEST_URI)
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	@Transactional
	void createTeamTest() throws Exception{
		Map<String, Object> w = new HashMap<>();
	    w.put("name", "test_Team");

	    String json = new ObjectMapper().writeValueAsString(w);
	    
		mvc.perform(post(TEAM_TEST_URI)
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	@Transactional
	void findTeamWithConditionTest() throws Exception{
  
		mvc.perform(get(TEAM_TEST_URI + "/1")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
		
		mvc.perform(get(TEAM_TEST_URI+"/3")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204))
				.andDo(print());
		
		mvc.perform(get(TEAM_TEST_URI+"/1/members")
				.header("Authorization", "Bearer XgEzXpJLnwVwYaJk")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
