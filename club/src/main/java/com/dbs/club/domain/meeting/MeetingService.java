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
    private final MemberService memberService;

    public MeetingService(MeetingRepository meetingRepository, MemberService memberService) {
        this.meetingRepository = meetingRepository;
        this.memberService = memberService;
    }

    @Transactional
    public long createMeeting(MeetingRequestDto.Create create) {
    Member member = memberService.getMember(create.memberId());
    Meeting meeting = create.toEntity(member);

    return meetingRepository.save(meeting).getId();
    }

    public Meeting getMeeting(Long meetingId) {
        Meeting findMeeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new MeetingException(ErrorCode.MEETING_NOT_FOUND));
        return findMeeting;
    }
}
