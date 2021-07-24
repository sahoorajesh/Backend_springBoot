package com.hospitalAggregator.spring.jwt.mongodb.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Document(collection = "admin")
public class Admin {
  @Transient
  public static final String SEQUENCE_NAME = "admins_sequence";

  @Id
  private long id;

  @DBRef
  @JsonProperty(access = Access.WRITE_ONLY)
  private Set<Role> roles = new HashSet<>();
  
  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
  
  public Admin() {
  }

  public Admin( 
		  String email,String username, String password
		  ) {
	  
    this.username = username;
    this.email = email;
    this.password = password;
    
    		}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
	    return roles;
	  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
	  
}
