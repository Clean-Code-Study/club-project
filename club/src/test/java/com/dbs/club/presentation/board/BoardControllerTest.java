package com.dbs.club.presentation.board;

import com.dbs.club.presentation.board.fixture.BoardControllerTestFixture;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createBoard_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        BoardRequestDto.Create createRequest =
                new BoardRequestDto.Create (memberId, "testTitle", "testContent");

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/boards")
                .then()
                .statusCode(201);
    }

    @Test
    void updateBoard_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        String url = BoardControllerTestFixture.createBoardFixture(memberId);
        Long boardId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));

        BoardRequestDto.Update request = new BoardRequestDto.Update(
                boardId,
                "updatedTitle",
                "updatedContent"
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/api/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void updateBoard_Fail_404() {
        long BoardId = 999L;

        BoardRequestDto.Update request = new BoardRequestDto.Update(
                BoardId,
                "updatedTitle",
                "updatedContent"
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/api/boards/" + BoardId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}