package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.model.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {
	List<TeamEntity> findById(Integer id);
	TeamEntity findByName(String name);
	
}