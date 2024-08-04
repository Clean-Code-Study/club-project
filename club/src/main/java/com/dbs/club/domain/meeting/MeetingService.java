package com.dbs.club.domain.meeting;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.infrastructure.meeting.MeetingRepository;
import org.springframework.stereotype.Service;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;

import java.time.LocalDate;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public Meeting saveMeeting() {

        Meeting meeting = Meeting.init(null, "testTitle", "testContent", "testLocation",
                LocalDate.of(2000, 1, 1), 100, 0, MeetingState.OPEN);
        return meetingRepository.save(meeting);
    }
}
