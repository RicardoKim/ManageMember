package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.model.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {
	TeamEntity findById(Long teamId);
	TeamEntity findByName(String name);
	
}