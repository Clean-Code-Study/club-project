package com.dbs.club.presentation.board;

import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.presentation.board.fixture.BoardControllerTestFixture;
import com.dbs.club.presentation.member.fixture.MemberControllerTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
                new BoardRequestDto.Create(memberId, "testTitle", "testContent");

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

    @Test
    void getBoard_Success() throws URISyntaxException {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));
        String url = BoardControllerTestFixture.createBoardFixture(memberId);

        Long BoardId = Long.parseLong(Paths.get(new URI(url).getPath()).getFileName().toString());


        given()
                .when()
                .get("/api/boards/{boardId}", BoardId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo(BoardControllerTestFixture.TITLE))
                .body("content", equalTo(BoardControllerTestFixture.CONTENT));
    }

    @Test
    void getBoard_Fail_404() {
        given()
                .when()
                .get("/api/boards/{boardId}", 100)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteBoard_Success() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        String boardurl = BoardControllerTestFixture.createBoardFixture(memberId);
        Long boardId = Long.parseLong(boardurl.substring(boardurl.lastIndexOf("/") + 1));

        given()
                .when()
                .delete("/api/boards/{boardId}", boardId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteBoard_Fail_404() {
        long boardId = 999L;

        given()
                .when()
                .delete("/api/boards/{boardId}", boardId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void createBoard_Profanity_Title() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        BoardRequestDto.Create createRequest = new BoardRequestDto.Create(
                memberId,
                "제목 욕설1",
                "내용"
        );

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message",equalTo(ErrorCode.BOARD_PROFANITY_FOUND.getMessage()));
    }

    @Test
    void createBoard_Profanity_Content() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));

        BoardRequestDto.Create createRequest = new BoardRequestDto.Create(
                memberId,
                "제목",
                "내용 욕설2"
        );

        given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo(ErrorCode.BOARD_PROFANITY_FOUND.getMessage()));
    }

    @Test
    void updateBoard_Profanity_Title() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));
        String url = BoardControllerTestFixture.createBoardFixture(memberId);
        Long boardId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));

        BoardRequestDto.Update updateRequest = new BoardRequestDto.Update(
                boardId,
                "제목 욕설1",
                " 내용"
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .patch("/api/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo(ErrorCode.BOARD_PROFANITY_FOUND.getMessage()));
    }

    @Test
    void updateBoard_Profanity_Content() {
        String memberUrl = MemberControllerTestFixture.createMemberFixture();
        Long memberId = Long.parseLong(memberUrl.substring(memberUrl.lastIndexOf("/") + 1));
        String url = BoardControllerTestFixture.createBoardFixture(memberId);
        Long boardId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));

        BoardRequestDto.Update updateRequest = new BoardRequestDto.Update(
                boardId,
                "제목",
                "내용 욕설2 "
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .patch("/api/boards/" + boardId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo(ErrorCode.BOARD_PROFANITY_FOUND.getMessage()));
    }
}
