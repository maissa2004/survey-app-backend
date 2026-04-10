package com.example.appenquetes1.dto.auth;

public class AuthResponse {
    private String token;
    private String username;
    private String role;
    private Integer userId;
    private String email;
    private String phone;

    public AuthResponse(String token, String username, String role, Integer userId) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.userId = userId;
    }

    public AuthResponse(String token, String username, String role, Integer userId, String email, String phone) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}