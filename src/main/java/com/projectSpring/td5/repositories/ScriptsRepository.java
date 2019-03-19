package com.projectSpring.td5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectSpring.td5.entities.Script;

@Repository
public interface ScriptsRepository extends JpaRepository<Script, Integer>{

}
