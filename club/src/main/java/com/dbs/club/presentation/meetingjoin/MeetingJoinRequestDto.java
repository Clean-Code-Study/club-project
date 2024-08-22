package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.domain.common.RegisterDeleteState;
import jakarta.validation.constraints.NotNull;

public class MeetingJoinRequestDto {

    public record Create(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "모임 ID는 필수입니다.")
            Long meetingId
    ) {

    }

    public record Cancel(

            @NotNull(message = "모임신청 ID는 필수입니다.")
            Long meetingJoinId,

            @NotNull(message = "멤버 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "모임 ID는 필수입니다.")
            Long meetingId,


            @NotNull(message = "상태는 필수입니다.")
            RegisterDeleteState status
    ) {

    }

}
