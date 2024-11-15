package com.dbs.club.domain.authentication;

public class OAuthException extends RuntimeException {
    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
