package com.application.notesApplication.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationRequest {
    private String username;
    String password;

    public String getUsername() {
        return username;
    }

}
