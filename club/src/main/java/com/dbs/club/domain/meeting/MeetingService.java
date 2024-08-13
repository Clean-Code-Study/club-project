package com.dbs.club.domain.meeting;

import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.meeting.MeetingRepository;
import com.dbs.club.presentation.meeting.MeetingRequestDto;
import org.springframework.stereotype.Service;

import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.meeting.exception.MeetingException;
import org.springframework.transaction.annotation.Transactional;

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
}
