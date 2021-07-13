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
  private String hospaddress;
  
  @NotBlank
  @Size(max = 1000)
  private String aboutus;

  @NotBlank
  @Pattern(regexp="(^$|[0-9]{4}[0-9]{2}[0-9]{4})")
  private String mobile;
  

  @NotBlank
  @URL
  @Size(max=75)
  private String website;

  private Boolean showdetails;
  private Boolean showbutton;
  private Boolean showapprovestatus;
  private Boolean showrejectstatus;
  
  @NotBlank
  private String ownership;
  
  private String year;
  
  public User() {
  }

  public User(String hospname, String hospspec, 
		  String district, String hospaddress, 
		  String ownership, String year, 
		  String email,String website, 
		  String mobile,String about,
		  String username,String password,
		  Boolean showbutton,Boolean showdetails,
		  Boolean showapprovestatus,Boolean showrejectstatus 
		   ) {
	  
    this.username = username;
    this.email = email;
    this.password = password;
    this.hospname = hospname;
    this.hospspec = hospspec;
    this.district = district;
    this.hospaddress = hospaddress;
    this.ownership = ownership;
    this.year = year;
    this.aboutus = about;
    this.website = website;
    this.mobile = mobile;
    this.showbutton = showbutton;
    this.showdetails = showdetails;
    this.showapprovestatus = showapprovestatus;
    this.showrejectstatus = showrejectstatus;
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

  public String gethospAddress() {
      return hospaddress;
  }

  public void sethospAddress(String hospaddress) {
      this.hospaddress = hospaddress;
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
  
  public Boolean getshowDetails() {
      return showdetails;
  }

  public void setshowDetails(Boolean showdetails) {
      this.showdetails = showdetails;
  }
  
  public Boolean getshowButton() {
      return showbutton;
  }

  public void setshowButton(Boolean showbutton) {
      this.showbutton = showbutton;
  }
  
  public Boolean getshowapproveStatus() {
      return showapprovestatus;
  }

  public void setshowapproveStatus(Boolean showapproveStatus) {
      this.showapprovestatus = showapproveStatus;
  }
  
  public Boolean getshowrejectStatus() {
      return showrejectstatus;
  }

  public void setshowrejectStatus(Boolean showrejectStatus) {
      this.showrejectstatus = showrejectStatus;
  }


}
