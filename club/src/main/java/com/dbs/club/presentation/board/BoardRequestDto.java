package com.dbs.club.presentation.board;

import jakarta.validation.constraints.NotNull;

public class BoardRequestDto {

    public record Create(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,
            @NotNull(message = "제목은 필수입니다.")
            String title,
            @NotNull(message = "내용은 필수입니다.")
            String content

    ) {

    }
}
