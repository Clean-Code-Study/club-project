package com.dbs.club.domain.authentication;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dbs.club.domain.member.OAuthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLogin {
  private String authorizationCode;

  public OAuthProvider oAuthProvider() {
    return OAuthProvider.KAKAO;
  }

  public MultiValueMap<String, String> makeBody() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("code", authorizationCode);
    return body;
  }
}
