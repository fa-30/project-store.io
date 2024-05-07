package com.project.ecommerce.api.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class forgetpass {
  
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min=6, max=32)
    private String password;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min=6, max=32)
    private String password2;
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    private String otp;
    public String getOtp2() {
        return otp2;
    }

    public void setOtp2(String otp2) {
        this.otp2 = otp2;
    }

    private String otp2;
    
    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

  
   
}
