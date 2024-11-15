package com.dbs.club.domain.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.oauth.client.KakaoOAuthClient;
import com.dbs.club.presentation.authentication.OAuthLoginResponseDto;

import com.dbs.club.domain.authentication.JwtTokenProvider;

@Service
public class OAuthService {
    private final KakaoOAuthClient kakaoOAuthClient;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OAuthService(KakaoOAuthClient kakaoOAuthClient, MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.kakaoOAuthClient = kakaoOAuthClient;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    public OAuthLoginResponseDto login(String code) {
        try {
            KakaoTokens kakaoTokens = kakaoOAuthClient.getToken(code);
            KakaoInfoResponse userInfo = kakaoOAuthClient.getUserInfo(kakaoTokens.getAccessToken());
            
            Member member = memberService.findOrCreateMember(userInfo);
            return createAuthTokens(member);
        } catch (Exception e) {
            if (e instanceof OAuthException) {
                throw new OAuthException("특정 오류 메시지", e);
            }
            throw new OAuthException("OAuth 로그인 중 오류가 발생했습니다.", e);
        }
    }

    private OAuthLoginResponseDto createAuthTokens(Member member) {
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return new OAuthLoginResponseDto(accessToken, refreshToken);
    }
}