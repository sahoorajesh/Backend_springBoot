package com.hospitalAggregator.spring.jwt.mongodb.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospitalAggregator.spring.jwt.mongodb.models.Admin;


public interface AdminRepository extends MongoRepository<Admin, String> {
	  Optional<Admin> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	}
