package com.projectSpring.td5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectSpring.td5.entities.Categorie;
import com.projectSpring.td5.entities.User;

public interface CategoriesRepository extends JpaRepository<Categorie, Integer>{
	
	public Categorie findById(int id);

}
