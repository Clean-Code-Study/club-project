package com.dbs.club.domain.member;

import java.time.LocalDate;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.RegisterDeleteState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    public static final String LOGIN_ID_REGEX = "^[a-zA-Z0-9]{5,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 30)
    private String loginId;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "contact", nullable = false, length = 20)
    private String contact;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "gender", nullable = false, length = 10)
    private MemberGenderType gender;

    @Column(name = "interest", length = 50)
    private String interest;

    @Column(name = "status", nullable = false, length = 30)
    private RegisterDeleteState status;

    public Member(String loginId, String password, String name, String contact, String nickname, LocalDate birth,
        MemberGenderType gender, String interest, RegisterDeleteState status) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.interest = interest;
        this.status = status;
    }
}
