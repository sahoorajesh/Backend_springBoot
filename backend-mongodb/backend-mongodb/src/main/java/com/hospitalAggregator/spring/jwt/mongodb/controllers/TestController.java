package com.hospitalAggregator.spring.jwt.mongodb.controllers;
import com.hospitalAggregator.spring.jwt.mongodb.models.Admin;
import com.hospitalAggregator.spring.jwt.mongodb.models.ERole;
import com.hospitalAggregator.spring.jwt.mongodb.models.Role;
import com.hospitalAggregator.spring.jwt.mongodb.models.User;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.AdminLoginRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.AdminSignupRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.JwtResponse;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.MessageResponse;
import com.hospitalAggregator.spring.jwt.mongodb.repository.AdminRepository;
import com.hospitalAggregator.spring.jwt.mongodb.repository.RoleRepository;
//import com.hospitalAggregator.spring.jwt.mongodb.payload.response.MessageResponse;
import com.hospitalAggregator.spring.jwt.mongodb.repository.UserRepository;
import com.hospitalAggregator.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.AdminDetailsImpl;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	AuthenticationManager authenticationManageradmin;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

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
	
	@PostMapping("/admin/signin")
	public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody AdminLoginRequest adminLoginRequest) {

		Authentication authentication = authenticationManageradmin.authenticate(
				new UsernamePasswordAuthenticationToken(adminLoginRequest.getUsername(), adminLoginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtTokenAdmin(authentication);
		
		AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();		
		List<String> roles = adminDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 adminDetails.getId(), 
												 adminDetails.getUsername(), 
												 adminDetails.getEmail(), 
												 roles));
		
//		return ResponseEntity.ok(authentication);
		
	}
	


	@PostMapping("/admin/signup")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminSignupRequest adminSignUpRequest) {
		if (adminRepository.existsByUsername(adminSignUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (adminRepository.existsByEmail(adminSignUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already registered"));
		}

		// Create new user's account
		Admin admin = new Admin(
							 adminSignUpRequest.getEmail(),
							 adminSignUpRequest.getUsername(),
							 encoder.encode(adminSignUpRequest.getPassword())
							 );

		Set<String> strRoles = adminSignUpRequest.getRoles();
		
		Set<Role> roles = new HashSet<>();

		
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

				break;
			case "hospital":
				Role modRole = roleRepository.findByName(ERole.ROLE_HOSPITAL_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(modRole);

				break;
			
			}
		});
	

		admin.setRoles(roles);
		admin.setId(sequenceGeneratorService.generateSequence(Admin.SEQUENCE_NAME));
		adminRepository.save(admin);

		return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
	}
	
}
