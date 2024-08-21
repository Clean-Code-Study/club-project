package com.dbs.club.presentation.member.fixture;

import static io.restassured.RestAssured.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.presentation.member.MemberRequestDto;

import io.restassured.http.ContentType;

public class MemberControllerTestFixture {
    public static final String PASSWORD = "password123!";
    public static final String NAME = "테스트이름";
    public static final String CONTACT = "01012345678";
    public static final LocalDate BIRTH = LocalDate.of(2000, 1, 1);
    public static final String INTEREST = "music";

    public static String createMemberFixture() {
        String uniqueLoginId = "user" + UUID.randomUUID().toString().substring(0, 8);
        String uniqueNickname = "nickName" + UUID.randomUUID().toString().substring(0, 8);

        MemberRequestDto.Create request = new MemberRequestDto.Create(
            uniqueLoginId, PASSWORD, NAME, CONTACT, uniqueNickname, BIRTH, MemberGenderType.FEMALE, INTEREST
        );

        String location = given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/api/members")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().header(HttpHeaders.LOCATION);

        return URI.create(location).getPath();
    }
}
