package com.AXA.AMFS.dto;

import javax.validation.constraints.NotBlank;

public class AccountUpdateDTO {
    private Long id;

    private String username;

    @NotBlank(message = "Username is required")
    private String password;

    public AccountUpdateDTO() {
    }

    public AccountUpdateDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountUpdateDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
