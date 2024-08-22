package com.dbs.club.presentation.meeting.fixture;

import com.dbs.club.presentation.meeting.MeetingRequestDto;
import io.restassured.http.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class MeetingControllerTestFixture {
    public static final String TITLE = "Test Title";
    public static final String CONTENT = "Test Content";
    public static final String LOCATION = "광진구 구의동";
    public static final LocalDate LOCALDATE = LocalDate.of(2024, 9, 1);
    public static final int JOIN_LIMIT = 5;

    public static String createMeetingFixture(Long memberId) {
        MeetingRequestDto.Create request = new MeetingRequestDto.Create(
                memberId,
                TITLE,
                CONTENT,
                LOCATION,
                LOCALDATE,
                JOIN_LIMIT
        );

        String location = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/meetings")
                .then()
                .statusCode(201)
                .extract().header(HttpHeaders.LOCATION);

        return URI.create(location).getPath();
    }
}
