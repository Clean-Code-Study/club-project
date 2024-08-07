package com.dbs.club.domain.meetingjoin;

import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.meeting.MeetingService;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.meetingjoin.MeetingJoinRepository;
import com.dbs.club.presentation.meetingjoin.MeetingJoinRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MeetingJoinService {

    private final MeetingJoinRepository meetingJoinRepository;
    private final MemberService memberService;
    private final MeetingService meetingService;

    public MeetingJoinService(MeetingJoinRepository meetingJoinRepository, MemberService memberService, MeetingService meetingService) {
        this.meetingJoinRepository = meetingJoinRepository;
        this.memberService = memberService;
        this.meetingService = meetingService;
    }

    @Transactional
    public long createMeetingJoin(MeetingJoinRequestDto.Create create) {

        Member member = memberService.getMember(create.memberId());

        Meeting meeting = meetingService.getMeeting(create.meetingId());

        MeetingJoin meetingJoin = MeetingJoin.builder()
                .member(member)
                .meeting(meeting)
                .status(RegisterDeleteState.REGISTERED)
                .build();


        return meetingJoinRepository.save(meetingJoin).getId();
    }
}
