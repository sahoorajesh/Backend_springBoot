package com.hospitalAggregator.spring.jwt.mongodb.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospitalAggregator.spring.jwt.mongodb.models.Enquiry;


public interface EnquiryRepository extends MongoRepository<Enquiry, String> {

	
  }
