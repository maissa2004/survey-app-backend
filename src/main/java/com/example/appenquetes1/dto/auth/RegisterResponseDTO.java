package com.example.appenquetes1.dto.auth;

public class RegisterResponseDTO {
    private Integer userId;
    private String username;
    private String message;
    public RegisterResponseDTO() {}

    public RegisterResponseDTO(Integer userId, String username, String message) {
        this.userId = userId;
        this.username = username;
        this.message = message;
    }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}