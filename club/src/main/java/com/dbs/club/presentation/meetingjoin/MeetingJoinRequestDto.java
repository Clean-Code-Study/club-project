package com.dbs.club.presentation.meetingjoin;

import jakarta.validation.constraints.NotNull;

public class MeetingJoinRequestDto {

    public record Create(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "모임 ID는 필수입니다.")
            Long meetingId
    ) {

    }

}
