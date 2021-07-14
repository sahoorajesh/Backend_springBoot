package com.hospitalAggregator.spring.jwt.mongodb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospitalAggregator.spring.jwt.mongodb.models.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  List<User> findByshowdetails(boolean showdetails);
  List<User> findByhospspec(String hospspec);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
