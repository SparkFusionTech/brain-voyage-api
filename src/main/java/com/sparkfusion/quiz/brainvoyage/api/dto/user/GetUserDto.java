package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import java.time.LocalDateTime;

public final class GetUserDto {

    private Long id;

    private String email;

    private String password;

    private String iconUrl;

    private LocalDateTime createdAt;

    public GetUserDto(Long id, String email, String password, String iconUrl, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.iconUrl = iconUrl;
        this.createdAt = createdAt;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
