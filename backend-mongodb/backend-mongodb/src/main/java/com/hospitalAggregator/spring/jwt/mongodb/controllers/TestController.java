package com.hospitalAggregator.spring.jwt.mongodb.controllers;
import com.hospitalAggregator.spring.jwt.mongodb.models.User;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.MessageResponse;
import com.hospitalAggregator.spring.jwt.mongodb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/searchhospital")
	public ResponseEntity<List<User>> getAllHospitals(@RequestParam(required = false) String hospspec) {
	  try {
	    List<User> hospitals = new ArrayList<User>();

	    if (hospspec == null)
	      userRepository.findAll().forEach(hospitals::add);
	    else
	      userRepository.findByhospspec(hospspec).forEach(hospitals::add);

	    if (hospitals.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(hospitals, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@GetMapping("/showhospital")
	public ResponseEntity<?> findByshowdetails() {

	    List<User> hospital = userRepository.findByshowdetails(true);

	    return new ResponseEntity<>(hospital, HttpStatus.OK);
	}
	
	@PutMapping("/edithospital"
			+ "/{username}")
	public ResponseEntity<?> updateHospital(@PathVariable("username") String username, @RequestBody User user) {
	  Optional<User> hospital = userRepository.findByUsername(username);

	  if (hospital.isPresent()) {
	    User _hospital = hospital.get();
	    _hospital.setHospname(user.getHospname());
	    _hospital.setHospspec(user.getHospspec());
	    _hospital.setDistrict(user.getDistrict()); 
	    _hospital.sethospAddress(user.gethospAddress());
	    _hospital.setOwnership(user.getOwnership());
	    _hospital.setYear(user.getYear());
	    _hospital.setAboutus(user.getAboutus());
	    _hospital.setWebsite(user.getWebsite());
	    _hospital.setMobile(user.getMobile());
	    _hospital.setshowButton(user.getshowButton());
	    _hospital.setshowDetails(user.getshowDetails());
	    _hospital.setshowapproveStatus(user.getshowapproveStatus());
	    _hospital.setshowrejectStatus(user.getshowrejectStatus());
	        	    
	    return new ResponseEntity<>(userRepository.save(_hospital), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}
	
}
