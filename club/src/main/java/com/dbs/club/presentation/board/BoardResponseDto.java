package com.dbs.club.presentation.board;

import com.dbs.club.domain.board.Board;

public class BoardResponseDto {

    public record Detail(
            Long boardId,
            String title,
            String content
    ) {
        public static Detail fromEntity(Board board) {
            return new Detail(
                    board.getId(),
                    board.getTitle(),
                    board.getContent()
            );
        }
    }
}
