package com.dbs.club.domain.meetingjoin;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.meeting.MeetingService;
import com.dbs.club.domain.meetingjoin.exception.MeetingJoinException;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.meetingjoin.MeetingJoinRepository;
import com.dbs.club.presentation.meetingjoin.MeetingJoinRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingJoinService {

    private static final int DECREMENT_ONE = 1;

    private final MeetingJoinRepository meetingJoinRepository;
    private final MemberService memberService;
    private final MeetingService meetingService;

    public MeetingJoinService(
            MeetingJoinRepository meetingJoinRepository,
            MemberService memberService,
            MeetingService meetingService
    ) {
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
                .status(MeetingJoinState.JOIN)
                .build();

        return meetingJoinRepository.save(meetingJoin).getId();
    }

    @Transactional(readOnly = true)
    public MeetingJoin getMeetingJoin(Long meetingJoinId) {
        return meetingJoinRepository.findById(meetingJoinId)
                .orElseThrow(() -> new MeetingJoinException(ErrorCode.MEETING_JOIN_NOT_FOUND));
    }

    @Transactional
    public void cancelMeetingJoin(Long meetingJoinId) {

        MeetingJoin meetingJoin = getMeetingJoin(meetingJoinId);


        if (meetingJoin.isMeetingDateBeforeToday()) {
            throw new MeetingJoinException(ErrorCode.MEETING_JOIN_CAN_NOT_CANCEL);
        }

        MeetingJoinState meetingJoinOldStatus = meetingJoin.getStatus();
        MeetingState meetingOldStatus = meetingJoin.getMeeting().getStatus();

        meetingJoin.cancel();

        if (canUpdateMeetingStatus(meetingJoinOldStatus, meetingOldStatus)) {
            meetingJoin.getMeeting().updateStatus(MeetingState.OPEN);
        }

        meetingJoin.getMeeting().updateJoinCount(meetingJoin.getMeeting().getJoinCount() - DECREMENT_ONE);

        meetingJoinRepository.save(meetingJoin);
    }

    private boolean canUpdateMeetingStatus(MeetingJoinState meetingJoinOldStatus, MeetingState meetingOldStatus) {
        return MeetingJoinState.JOIN == meetingJoinOldStatus && MeetingState.FULL == meetingOldStatus;
    }

}
