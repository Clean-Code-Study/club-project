package com.dbs.club.presentation.member;

import java.time.LocalDate;

import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.domain.member.MemberStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MemberRequestDto {

    public record Create(
        @Pattern(regexp = Member.LOGIN_ID_REGEX, message = "아이디는 5자리 이상 영문 소문자와 숫자만 가능합니다.")
        @NotNull(message = "로그인 아이디는 필수입니다.")
        String loginId,

        @Pattern(regexp = Member.PASSWORD_REGEX, message = "비밀번호는 8자리 이상 영문 소문자와 숫자, 특수문자를 포함해야 합니다.")
        @NotNull(message = "비밀번호는 필수입니다.")
        String password,

        @NotNull(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "연락처는 필수입니다.")
        String contact,

        @NotNull(message = "닉네임은 필수입니다.")
        String nickname,

        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,

        @NotNull(message = "성별은 필수입니다.")
        MemberGenderType gender,

        String interest
    ) {

        public Member toEntity() {
            return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .contact(this.contact)
                .nickname(this.nickname)
                .birth(this.birth)
                .gender(this.gender)
                .interest(this.interest)
                .status(MemberStatus.REGISTERED)
                .build();
        }
    }

    public record Update(
        @Pattern(regexp = Member.PASSWORD_REGEX, message = "비밀번호는 8자리 이상 영문 소문자와 숫자, 특수문자를 포함해야 합니다.")
        @NotNull(message = "비밀번호는 필수입니다.")
        String password,

        @NotNull(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "연락처는 필수입니다.")
        String contact,

        @NotNull(message = "닉네임은 필수입니다.")
        String nickname,

        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,

        String interest
    ) {
    }
}
