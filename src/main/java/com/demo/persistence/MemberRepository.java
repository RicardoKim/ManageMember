package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
	MemberEntity findById(Long id);
	List<MemberEntity> findByName(String name);
	List<MemberEntity> findByAge(Integer age);
	List<MemberEntity> findByTeamId(Long teamId);
	List<MemberEntity> findByGender(String gender);
	
	@Query(value = "SELECT * FROM member WHERE :key = :value", nativeQuery=true)
	List<MemberEntity> findByCondition(@Param("key") String key, @Param("value")String value);
	
}