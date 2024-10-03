package com.sparkfusion.quiz.brainvoyage.api.dto.user;

public class UserExistsDto {

    private Boolean exists;

    public UserExistsDto(Boolean exists) {
        this.exists = exists;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
}
