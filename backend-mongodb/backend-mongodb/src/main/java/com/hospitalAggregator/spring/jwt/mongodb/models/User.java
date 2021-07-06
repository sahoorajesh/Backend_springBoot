package com.hospitalAggregator.spring.jwt.mongodb.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @DBRef
  private Set<Role> roles = new HashSet<>();
//  private String roles;
  
  @NotBlank
  @Size(min = 3, max = 100)
  private String hospname;
  
  @NotBlank
  @Size(min = 3, max = 50)
  private String hospspec;

  @NotBlank
  @Size(min = 3, max = 50)
  private String district;

  @NotBlank
  @Size(min = 3, max = 50)
  private String state;
  
  @NotBlank
  @Size(max = 1000)
  private String aboutus;

  @NotBlank
  @Pattern(regexp="(^$|[0-9]{4}-[0-9]{2}-[0-9]{4})")
  private String mobile;
  

  @NotBlank
  @URL
  @Size(max=75)
  private String website;

  
  @NotBlank
  private String ownership;
  

  private String year;
  
  public User() {
  }

  public User(String hospname, String hospspec, 
		  String district, String state, 
		  String ownership, String year, 
		  String username, String email, 
		  String password, String about,
		  String mobile, String website) {
	  
    this.username = username;
    this.email = email;
    this.password = password;
    this.hospname = hospname;
    this.hospspec = hospspec;
    this.district = district;
    this.state = state;
    this.ownership = ownership;
    this.year = year;
    this.aboutus = about;
    this.website = website;
    this.mobile = mobile;
    
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
  
  public String getHospname() {
      return hospname;
  }

  public void setHospname(String hospname) {
      this.hospname = hospname;
  }
  
  public String getHospspec() {
      return hospspec;
  }

  public void setHospspec(String hospspec) {
      this.hospspec = hospspec;
  }
  
  public String getDistrict() {
      return district;
  }
  
  public void setDistrict(String district) {
      this.district = district;
  }

  public String getState() {
      return state;
  }

  public void setState(String state) {
      this.state = state;
  }

  public String getOwnership() {
      return this.ownership;
  }
    
  public void setOwnership(String ownership) {
	this.ownership = ownership;
  }
	
  public String getYear() {
	return year;
  }
	
  public void setYear(String year) {
	this.year = year;
  }
  
  public String getWebsite() {
      return website;
  }

  public void setWebsite(String website) {
      this.website = website;
  }

  public String getMobile() {
      return mobile;
  }

  public void setMobile(String mobile) {
      this.mobile = mobile;
  }
  
  public String getAboutus() {
      return aboutus;
  }

  public void setAboutus(String aboutus) {
      this.aboutus = aboutus;
  }
	
}
