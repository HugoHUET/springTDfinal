package com.projectSpring.td5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectSpring.td5.entities.Language;
import com.projectSpring.td5.entities.User;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Integer>{
	
	public Language findById(int id);
	
}
