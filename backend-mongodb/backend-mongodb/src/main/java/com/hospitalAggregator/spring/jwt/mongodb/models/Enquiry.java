package com.hospitalAggregator.spring.jwt.mongodb.models;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "enquiry")
public class Enquiry {
  @Transient
  public static final String SEQUENCE_NAME = "enquiry_sequence";

  @Id
  private long id;


  @NotBlank
  @Size(max = 100)
  private String name;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

 
  @NotBlank
  @Size(min = 3, max = 50)
  private String address;
  
  @NotBlank
  @Size(max = 1000)
  private String query;

  @NotBlank
  @Pattern(regexp="(^$|[0-9]{4}[0-9]{2}[0-9]{4})")
  private String mobile;
  

  
  public Enquiry() {
  }

  public Enquiry(String name, String address,
		  String email, String mobile,
		  String query
		  ) {
	  
    this.name = name;
    this.address = address;
    this.email = email;
    this.mobile = mobile;
    this.query = query;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setNname(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

 

  public String getAddress() {
      return address;
  }

  public void setAddress(String address) {
      this.address = address;
  }

  

  public String getMobile() {
      return mobile;
  }

  public void setMobile(String mobile) {
      this.mobile = mobile;
  }
  
  public String getQuery() {
      return query;
  }

  public void setQuery(String query) {
      this.query = query;
  }
  
 
}
