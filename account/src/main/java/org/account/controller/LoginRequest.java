package org.account.controller;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "로그인 아이디는 필수입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
