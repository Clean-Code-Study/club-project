package com.dbs.club.presentation.board;

import com.dbs.club.domain.board.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dbs.club.presentation.board.BoardController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class BoardController {

    static final String ROOT_URL = "/api/boards";
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(@Valid @RequestBody BoardRequestDto.Create create) {
        long boardId = boardService.createBoard(create);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
                .pathSegment("{boardId}").buildAndExpand(boardId).toUri();

        return ResponseEntity.created(locationUri).build();
    }
}
