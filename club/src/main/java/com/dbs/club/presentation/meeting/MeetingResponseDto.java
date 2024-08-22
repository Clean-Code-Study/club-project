package com.dbs.club.presentation.meeting;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.meeting.Meeting;

import java.time.LocalDate;

public class MeetingResponseDto {
    public record List(
        Long id,
        String title,
        String location,
        LocalDate date,
        int joinLimit,
        int joinCount,
        MeetingState status,
        String memberNickname
    ) {
        public static List fromEntity(Meeting meeting) {
            return new List(
                    meeting.getId(),
                    meeting.getTitle(),
                    meeting.getLocation(),
                    meeting.getDate(),
                    meeting.getJoinLimit(),
                    meeting.getJoinCount(),
                    meeting.getStatus(),
                    meeting.getMember().getNickname()
            );
        }
    }
}
