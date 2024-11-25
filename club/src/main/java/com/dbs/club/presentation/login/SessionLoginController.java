package com.dbs.club.presentation.login;

import com.dbs.club.domain.sessionlogin.SessionLoginService;
import com.dbs.club.domain.member.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SessionLoginController {

    private final SessionLoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody SessionLoginDto loginDto,
                                   HttpServletRequest request) {

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        // 세션 생성 및 저장
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity.ok("로그인 성공! 세션 ID: " + session.getId());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok("로그아웃 성공!");
    }

}
