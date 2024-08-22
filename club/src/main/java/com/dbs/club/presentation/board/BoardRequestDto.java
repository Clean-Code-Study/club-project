package com.dbs.club.presentation.board;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BoardRequestDto {

    public record Create(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "제목은 필수입니다.")
            @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
            String title,

            @NotNull(message = "내용은 필수입니다.")
            @Size(max = 1500, message = "내용은 최대 1500자까지 가능합니다.")
            String content

    ) {

    }

    public record Update(

            @NotNull(message = "게시물 ID는 필수입니다.")
            Long boardId,

            @NotNull(message = "제목은 필수입니다.")
            @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
            String title,

            @NotNull(message = "내용은 필수입니다.")
            @Size(max = 1500, message = "내용은 최대 1500자까지 가능합니다.")
            String content
    ){

    }
}
