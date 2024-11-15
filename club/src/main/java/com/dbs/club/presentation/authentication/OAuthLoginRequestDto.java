package com.dbs.club.presentation.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthLoginRequestDto {
    private String authorizationCode;

    public OAuthLoginRequestDto(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}