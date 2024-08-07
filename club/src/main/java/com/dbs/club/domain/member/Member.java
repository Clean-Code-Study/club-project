package com.dbs.club.domain.member;

import java.time.LocalDate;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.RegisterDeleteState;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

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

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private MemberGenderType gender;

    @Column(name = "interest", length = 50)
    private String interest;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
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

    public static Member init(String loginId, String password, String name, String contact, String nickname,
        LocalDate birth, MemberGenderType gender, String interest, RegisterDeleteState status) {
        return new Member(loginId, password, name, contact, nickname, birth, gender, interest, status);
    }
}
