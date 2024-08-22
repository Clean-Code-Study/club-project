package com.dbs.club.presentation.meeting;

import static io.restassured.RestAssured.*;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeetingControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createMeeting_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        MeetingRequestDto.Create createRequest =
                new MeetingRequestDto.Create (memberId, "testTitle", "testContent", "testLocation", LocalDate.of(2024, 9, 1), 5);

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/meetings")
                .then();
//                .statusCode(201);
    }

}