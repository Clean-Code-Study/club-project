package com.dbs.club.presentation.meeting;

import com.dbs.club.presentation.meeting.fixture.MeetingControllerTestFixture;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

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
                new MeetingRequestDto.Create (memberId, "testTitle", "testContent", "구로구 구로동", LocalDate.of(2024, 9, 1), 5);

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/meetings")
                .then()
                .statusCode(201);
    }

    @Test
    void updateMeeting_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        String url = MeetingControllerTestFixture.createMeetingFixture(memberId);

        MeetingRequestDto.Update request = new MeetingRequestDto.Update(
                "수정할 제목",
                "수정할 내용",
                "구로구 구로동",
                LocalDate.of(2024, 12, 31),
                10
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
    void updateMeeting_Fail_404() {
        long meetingId = 99L;

        MeetingRequestDto.Update request = new MeetingRequestDto.Update(
                "수정할 제목",
                "수정할 내용",
                "구로구 구로동",
                LocalDate.of(2024, 12, 31),
                10
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/api/meetings/" + meetingId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
