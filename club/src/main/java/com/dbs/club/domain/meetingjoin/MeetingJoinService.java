package com.dbs.club.domain.meetingjoin;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.common.RegisterDeleteState;
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

import java.time.LocalDate;

@Service
public class MeetingJoinService {

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
                .status(RegisterDeleteState.REGISTERED)
                .build();

        return meetingJoinRepository.save(meetingJoin).getId();
    }

    @Transactional(readOnly = true)
    public MeetingJoin getMeetingJoin(Long meetingJoinId) {
        return meetingJoinRepository.findById(meetingJoinId)
                .orElseThrow(() -> new MeetingJoinException(ErrorCode.MEETINGJOIN_NOT_FOUND));
    }

    @Transactional
    public void cancelMeetingJoin(MeetingJoinRequestDto.Cancel request, Long meetingJoinId) {

        MeetingJoin meetingJoin = getMeetingJoin(meetingJoinId);

        if(meetingJoin.getMeeting().getDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("모임 참여 후에는 취소할 수 없습니다.");
        }

        RegisterDeleteState oldStatus = meetingJoin.getStatus();

        meetingJoin.cancel(request.status());

        if(oldStatus == RegisterDeleteState.REGISTERED && meetingJoin.getMeeting().getJoinCount() == meetingJoin.getMeeting().getJoinLimit()) {
            meetingJoin.getMeeting().setStatus(MeetingState.OPEN);
        }

        meetingJoin.getMeeting().setJoinCount(meetingJoin.getMeeting().getJoinCount() - 1);

        meetingJoinRepository.save(meetingJoin);
    }
}
