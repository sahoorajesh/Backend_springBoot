package com.hospitalAggregator.spring.jwt.mongodb.controllers;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospitalAggregator.spring.jwt.mongodb.models.Admin;
import com.hospitalAggregator.spring.jwt.mongodb.models.ERole;
import com.hospitalAggregator.spring.jwt.mongodb.models.Role;
import com.hospitalAggregator.spring.jwt.mongodb.models.User;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.AdminLoginRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.AdminSignupRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.LoginRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.request.SignupRequest;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.JwtResponse;
import com.hospitalAggregator.spring.jwt.mongodb.payload.response.MessageResponse;
import com.hospitalAggregator.spring.jwt.mongodb.repository.RoleRepository;
import com.hospitalAggregator.spring.jwt.mongodb.repository.UserRepository;
import com.hospitalAggregator.spring.jwt.mongodb.repository.AdminRepository;
import com.hospitalAggregator.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.AdminDetailsImpl;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.SequenceGeneratorService;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	
	@PostMapping("/admin/signin")
	public AdminDetailsImpl authenticateAdmin(@Valid @RequestBody AdminLoginRequest adminLoginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(adminLoginRequest.getUsername(), adminLoginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtTokenAdmin(authentication);
		
		AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();		
		List<String> roles = adminDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
//
//		return ResponseEntity.ok(new JwtResponse(jwt, 
//												 adminDetails.getId(), 
//												 adminDetails.getUsername(), 
//												 adminDetails.getEmail(), 
//												 roles));
		return adminDetails;
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already registered"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getHospname(),signUpRequest.getHospspec(),
							 signUpRequest.getDistrict(),signUpRequest.gethospAddress(),
							 signUpRequest.getOwnership(),signUpRequest.getYear(),
							 signUpRequest.getEmail(),signUpRequest.getWebsite(),
							 signUpRequest.getMobile(),signUpRequest.getAboutus(),
							 signUpRequest.getUsername(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getshowButton(),signUpRequest.getshowDetails(),
							 signUpRequest.getshowapproveStatus(),signUpRequest.getshowrejectStatus());

		Set<String> strRoles = signUpRequest.getRoles();
		
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
	

		user.setRoles(roles);
		user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Hospital registered successfully!"));
	}
	
	

//	
	

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
