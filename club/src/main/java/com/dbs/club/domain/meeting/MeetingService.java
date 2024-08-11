package com.dbs.club.domain.meeting;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.meeting.exception.MeetingException;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.infrastructure.meeting.MeetingRepository;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public Meeting saveMeeting() {
        Member member = Member.init("testId", "1234", "testName", "01011112222",
            "testNickname", LocalDate.of(2000, 1, 1), MemberGenderType.FEMALE,
            "testInterest", RegisterDeleteState.REGISTERED);

        Meeting meeting = Meeting.init(member, "testTitle", "testContent", "testLocation",
            LocalDate.of(2000, 1, 1), 100, 0, MeetingState.OPEN);
        return meetingRepository.save(meeting);
    }

    public Meeting getMeeting(Long meetingId) {
        Meeting findMeeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new MeetingException(ErrorCode.MEETING_NOT_FOUND));
        return findMeeting;
    }
}
