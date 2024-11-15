package com.dbs.club.infrastructure.oauth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dbs.club.domain.authentication.KakaoInfoResponse;
import com.dbs.club.domain.authentication.KakaoTokens;

import org.springframework.http.HttpMethod;

@Component
public class KakaoOAuthClient {
    private final RestTemplate restTemplate;
    private final String clientId;
    private final String authUrl;
    private final String apiUrl;

    public KakaoOAuthClient(
        RestTemplate restTemplate,
        @Value("${oauth.kakao.client-id}") String clientId,
        @Value("${oauth.kakao.url.auth}") String authUrl,
        @Value("${oauth.kakao.url.api}") String apiUrl
    ) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.authUrl = authUrl;
        this.apiUrl = apiUrl;
    }

    public KakaoTokens getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("code", code);
    
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    
        return restTemplate.postForObject(
            authUrl + "/oauth/token",
            request,
            KakaoTokens.class
        );
    }

    public KakaoInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
            apiUrl + "/v2/user/me",
            HttpMethod.GET,
            request,
            KakaoInfoResponse.class
        ).getBody();
    }
}
