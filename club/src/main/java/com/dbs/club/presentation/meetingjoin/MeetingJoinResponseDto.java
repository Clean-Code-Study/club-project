package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.meetingjoin.MeetingJoin;
import com.dbs.club.domain.meetingjoin.MeetingJoinState;

public class MeetingJoinResponseDto { //조회
    public record List(
            long id,
            String title,
            MeetingJoinState status
    ) {


        public static List fromEntity(MeetingJoin meetingJoin, Meeting meeting) {
            return new List(
                    meetingJoin.getId(),
                    meeting.getTitle(),
                    meetingJoin.getStatus()
            );
        }
    }
}
