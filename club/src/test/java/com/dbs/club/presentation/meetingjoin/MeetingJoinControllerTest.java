package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.presentation.meeting.MeetingRequestDto;
import com.dbs.club.presentation.meeting.fixture.MeetingControllerTestFixture;
import com.dbs.club.presentation.meetingjoin.fixture.MeetingJoinControllerTestFixture;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeetingJoinControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createMeetingJoin_Success() {

        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        String meetingUrl = MeetingControllerTestFixture.createMeetingFixture(memberId);
        Long meetingId = Long.parseLong(meetingUrl.substring(meetingUrl.lastIndexOf("/") + 1));

        MeetingJoinRequestDto.Create createRequest = new MeetingJoinRequestDto.Create(memberId, meetingId);

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/meeting-joins")
                .then()
                .statusCode(201);
    }

    @Test
    void cancelMeetingJoin_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        String meetingUrl = MeetingControllerTestFixture.createMeetingFixture(memberId);
        Long meetingId = Long.parseLong(meetingUrl.substring(meetingUrl.lastIndexOf("/") + 1));

        String url = MeetingJoinControllerTestFixture.createMeetingJoinFixture(memberId, meetingId);
        long meetingJoinId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/api/meeting-joins/" + meetingJoinId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void cancelMeetingJoin_Fail_400() {

        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        MeetingRequestDto.Create request = new MeetingRequestDto.Create(
                memberId,
                "hi",
                "CONTENT",
                "광진구 구의동",
                LocalDate.of(2024, 8, 1),
                5
        );

        String location = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/meetings")
                .then()
                .statusCode(201)
                .extract().header(HttpHeaders.LOCATION);

        String meetingUrl = URI.create(location).getPath();
        Long meetingId = Long.parseLong(meetingUrl.substring(meetingUrl.lastIndexOf("/") + 1));

        String url = MeetingJoinControllerTestFixture.createMeetingJoinFixture(memberId, meetingId);
        long meetingJoinId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/api/meeting-joins/" + meetingJoinId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
