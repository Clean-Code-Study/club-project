package com.dbs.club.infrastructure.board;

import com.dbs.club.domain.board.BoardService;
import com.dbs.club.presentation.board.BoardController;
import com.dbs.club.presentation.board.BoardRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    public void createBoard_성공() throws Exception {

        BoardRequestDto.Create requestDto =
                new BoardRequestDto.Create(1L,"testTitle","testContent");


        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberId\": 1, \"title\": \"testTitle\", \"content\": \"testContent\"}"))
                .andExpect(status().isCreated());
    }
}
