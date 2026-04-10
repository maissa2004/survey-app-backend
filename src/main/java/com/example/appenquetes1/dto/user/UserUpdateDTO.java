package com.example.appenquetes1.dto.user;

import com.example.appenquetes1.entity.User;

public class UserUpdateDTO {
    private String email;
    private String phone;
    private User.Role role;
    private Boolean isActive;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}