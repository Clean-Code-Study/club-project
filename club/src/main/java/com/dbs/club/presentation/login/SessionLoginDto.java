package com.dbs.club.presentation.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SessionLoginDto {

    @NotNull(message = "로그인 아이디는 필수입니다.")
    private String loginId;
    @NotEmpty
    private String password;
}
