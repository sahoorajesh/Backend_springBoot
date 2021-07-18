package com.hospitalAggregator.spring.jwt.mongodb.payload.request;
import javax.validation.constraints.*;

public class EnquiryRequest {
	
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String address;
    
    @NotBlank
    @Size(max = 1000)
    private String query;
 
    @NotBlank
    @Pattern(regexp="(^$|[0-9]{4}[0-9]{2}[0-9]{4})")
    private String mobile;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
  
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
  
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
   
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
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
