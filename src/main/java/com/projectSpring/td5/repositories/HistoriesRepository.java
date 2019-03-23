package com.projectSpring.td5.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectSpring.td5.entities.History;

@Repository
public interface HistoriesRepository extends JpaRepository<History, Integer>{
	public List<History> findAllByScript_id(int id);
}
