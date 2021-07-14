package com.hospitalAggregator.spring.jwt.mongodb.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalAggregator.spring.jwt.mongodb.models.User;
import com.hospitalAggregator.spring.jwt.mongodb.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	
	
	@Transactional
	public UserDetails loadUserByStatus(boolean showdetails) {
		List<User> user = userRepository.findByshowdetails(showdetails);
				

		return UserDetailsImpl.build(user);
	}
	
	@Transactional
	public UserDetails loadUserByHospspec(String hospspec) {
		List<User> user = userRepository.findByhospspec(hospspec);
				

		return UserDetailsImpl.build(user);
	}

}
