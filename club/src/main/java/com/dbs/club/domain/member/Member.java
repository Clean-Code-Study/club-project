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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private MemberGenderType gender;

    @Column(name = "interest", length = 50)
    private String interest;

    @Column(name = "status", nullable = false, length = 30)
    private RegisterDeleteState status;

}
