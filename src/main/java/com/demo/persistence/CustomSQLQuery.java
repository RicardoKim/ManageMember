package com.demo.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.demo.model.MemberEntity;

@Component
public class CustomSQLQuery {
	
	@PersistenceContext
	private EntityManager entityManger;
	
	public List<MemberEntity> customSQLSearchWithCondition(String key, String value) {
		String jpqlString = "SELECT * FROM notidev.member WHERE member." + key + " = ?1";
		try {
			List<MemberEntity> result = entityManger.createNativeQuery(jpqlString, MemberEntity.class).setParameter(1, value).getResultList();
			return result;
		}catch(Exception e) {
			return null;
		}
		
	}
}
