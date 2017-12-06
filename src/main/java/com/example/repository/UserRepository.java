package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	//查询
	List<User> findById(String id);
	//修改
	//int saveAndFlush(Employee);
}
