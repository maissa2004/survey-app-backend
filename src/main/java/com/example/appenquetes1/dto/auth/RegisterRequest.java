package com.example.appenquetes1.dto.auth;

import com.example.appenquetes1.entity.User;

public class RegisterRequest {
    private String username;
    private String email;
    private String phone;
    private String password;
    private User.Role role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
}