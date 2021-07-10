package com.hospitalAggregator.spring.jwt.mongodb.controllers;
import com.hospitalAggregator.spring.jwt.mongodb.models.User;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.MessageResponse;
import com.hospitalAggregator.spring.jwt.mongodb.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/hospital")
	public String hospitalAccess() {
		return "Hospital Content";
	}

	@GetMapping("/admin")
	public String adminAccess() {
		return "Admin Content";
	}
	
	
	@GetMapping("/getalluser")
	public Iterable<User> user() {
        return userRepository.findAll();
    }
	
	@DeleteMapping("/deletehospital/{username}")
	public ResponseEntity<?> delete(@PathVariable String username) {
        Optional<User> hospital = userRepository.findByUsername(username);
        User user = hospital.get();
        userRepository.delete(user);

        return ResponseEntity.ok(new MessageResponse("Hospital deleted successfully!"));
    }
	
}
