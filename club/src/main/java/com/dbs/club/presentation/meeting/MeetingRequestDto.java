package com.dbs.club.presentation.meeting;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.member.Member;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class MeetingRequestDto {

    public record Create(
        @NotNull(message = "회원 정보는 필수입니다.")
        Long memberId,

        @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
        @NotNull(message = "제목은 필수입니다.")
        String title,

        @Size(max = 500, message = "내용은 최대 500자까지 가능합니다.")
        @NotNull(message = "내용은 필수입니다.")
        String content,

        @Pattern(regexp = Meeting.LOCATION_REGEX, message = "모임 장소는 _구 _동 형식으로 작성해야 합니다.")
        @NotNull(message = "장소는 필수입니다.")
        String location,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "참가 인원은 필수입니다.")
        int joinLimit
        ) {

        public Meeting toEntity(Member member) {
            return Meeting.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .location(this.location)
                .date(this.date)
                .joinLimit(this.joinLimit)
                .status(MeetingState.OPEN)
                .build();
        }
    }
}
