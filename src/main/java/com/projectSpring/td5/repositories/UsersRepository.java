package com.projectSpring.td5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectSpring.td5.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>{
	public User findByLogin(String login);
	public User findByPassword(String password);
}
