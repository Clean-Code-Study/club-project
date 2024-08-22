package com.dbs.club.presentation.meetingjoin.fixture;

import com.dbs.club.domain.meetingjoin.MeetingJoin;
import com.dbs.club.presentation.meetingjoin.MeetingJoinRequestDto;
import io.restassured.http.ContentType;
import org.springframework.http.HttpHeaders;

import java.net.URI;

import static io.restassured.RestAssured.given;

public class MeetingJoinControllerTestFixture {
    public static String createMeetingJoinFixture(Long memberId, Long meetingId) {
        MeetingJoinRequestDto.Create request = new MeetingJoinRequestDto.Create(
                memberId,
                meetingId
        );

        String location = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/meeting-joins")
                .then()
                .statusCode(201)
                .extract().header(HttpHeaders.LOCATION);

        return URI.create(location).getPath();
    }
}
