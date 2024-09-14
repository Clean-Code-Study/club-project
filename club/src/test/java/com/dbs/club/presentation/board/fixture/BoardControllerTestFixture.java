package com.dbs.club.presentation.board.fixture;

import com.dbs.club.presentation.board.BoardRequestDto;
import io.restassured.http.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static io.restassured.RestAssured.given;

public class BoardControllerTestFixture {
    public static final String TITLE = "제목입니다";
    public static final String CONTENT = "내용입니다";

    public static String createBoardFixture(Long memberId) {

        BoardRequestDto.Create request = new BoardRequestDto.Create(
                memberId,
                TITLE,
                CONTENT
        );

        String location = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header(HttpHeaders.LOCATION);

        return URI.create(location).getPath();
    }
}
