package com.dbs.club.presentation.board;

import com.dbs.club.domain.board.Board;
import com.dbs.club.domain.board.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardRequestDto.Update request
    ){
        boardService.updateBoard(request, boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto.Detail> getBoard(@PathVariable Long boardId){
        Board board = boardService.getBoard(boardId);
        BoardResponseDto.Detail response = BoardResponseDto.Detail.fromEntity(board);
        return ResponseEntity.ok(response);
    }
}
