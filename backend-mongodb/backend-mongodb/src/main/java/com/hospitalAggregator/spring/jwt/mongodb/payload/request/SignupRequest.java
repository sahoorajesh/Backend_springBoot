package com.hospitalAggregator.spring.jwt.mongodb.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.URL;
 
public class SignupRequest {
	
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    
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
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @URL
    @Size(max=75)
    private String website;
    
    private Set<String> roles;
    
    @NotBlank
    private String ownership;
    

    private String year;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    private boolean showdetails;
    private boolean showbutton;
    private boolean showapprovestatus;
    private boolean showrejectstatus;
  
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
	
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> Roles) {
      this.roles = Roles;
    
    }
    
    public boolean getshowDetails() {
        return showdetails;
    }
 
    public void setshowDetails(boolean showdetails) {
        this.showdetails = showdetails;
    }
    
    public boolean getshowButton() {
        return showbutton;
    }
 
    public void setshowButton(boolean showbutton) {
        this.showbutton = showbutton;
    }
    
    public boolean getshowapproveStatus() {
        return showapprovestatus;
    }
 
    public void setshowapproveStatus(boolean showapproveStatus) {
        this.showapprovestatus = showapproveStatus;
    }
    
    public boolean getshowrejectStatus() {
        return showrejectstatus;
    }
 
    public void setshowrejectStatus(boolean showrejectStatus) {
        this.showrejectstatus = showrejectStatus;
    }
    
}
