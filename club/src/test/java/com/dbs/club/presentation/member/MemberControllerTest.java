package com.dbs.club.presentation.member;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;

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

        given()
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

        given()
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

        given()
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

        given()
            .contentType(ContentType.JSON)
            .body(createRequest)
            .when()
            .post("/api/members")
            .then()
            .statusCode(400)
            .body("code", equalTo("COMMON_INVALID_PARAMETER"));
    }

    @Test
    void updateMember_Success() {
        String url = MemberControllerTestFixture.createMemberFixture();

        MemberRequestDto.Update request = new MemberRequestDto.Update(
            "password@1234",
            "수정할회원이름",
            "01012345678",
            "testNickname2",
            LocalDate.of(2000, 1, 1),
            "music"
        );

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put(url)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void updateMember_Fail_404() {
        long memberId = 99L;

        MemberRequestDto.Update request = new MemberRequestDto.Update(
            "password@1234",
            "수정할회원이름",
            "01012345678",
            "testNickname2",
            LocalDate.of(2000, 1, 1),
            "music"
        );

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/api/members/" + memberId)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateMember_Fail_400() {
        String url = MemberControllerTestFixture.createMemberFixture();

        MemberRequestDto.Update request = new MemberRequestDto.Update(
            "password",
            "수정할회원이름",
            "01012345678",
            "testNickname2",
            LocalDate.of(2000, 1, 1),
            "music"
        );

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put(url)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
