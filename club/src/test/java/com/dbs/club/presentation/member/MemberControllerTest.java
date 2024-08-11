package com.dbs.club.presentation.member;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.dbs.club.domain.member.MemberGenderType;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createMember_Success() {
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            "user123",
            "password@123",
            "회원이름",
            "01012345678",
            "testNickname",
            LocalDate.of(2000, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(createRequest)
            .when()
            .post("/api/members")
            .then()
            .statusCode(201);
    }

    @Test
    void createMember_Invalid_LoginId() {
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            "mem", // Invalid loginId
            "password@123",
            "회원이름",
            "01012345678",
            "testNickname",
            LocalDate.of(2000, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(createRequest)
            .when()
            .post("/api/members")
            .then()
            .statusCode(400)
            .body("code", equalTo("COMMON_INVALID_PARAMETER"));
    }

    @Test
    void createMember_Invalid_Password() {
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            "member123",
            "password@", // Invalid password
            "회원이름",
            "01012345678",
            "testNickname",
            LocalDate.of(2000, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(createRequest)
            .when()
            .post("/api/members")
            .then()
            .statusCode(400)
            .body("code", equalTo("COMMON_INVALID_PARAMETER"));
    }

    @Test
    void createMember_NotNull_Name() {
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            "member123",
            "password@123",
            null, // Null name
            "01012345678",
            "testNickname",
            LocalDate.of(2000, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(createRequest)
            .when()
            .post("/api/members")
            .then()
            .statusCode(400)
            .body("code", equalTo("COMMON_INVALID_PARAMETER"));
    }
}