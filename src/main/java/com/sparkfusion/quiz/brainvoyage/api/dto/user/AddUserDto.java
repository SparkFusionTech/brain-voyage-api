package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AddUserDto {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public AddUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "Email must not be blank") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(
            @NotBlank(message = "Email must not be blank")
            @Email(message = "Email should be valid")
            String email
    ) {
        this.email = email;
    }

    public @NotBlank(message = "Password must not be blank") @Size(min = 8, message = "Password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(
            @NotBlank(message = "Password must not be blank")
            @Size(min = 8, message = "Password must be at least 8 characters long")
            String password
    ) {
        this.password = password;
    }
}
