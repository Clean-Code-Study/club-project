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
import com.dbs.club.presentation.meetingjoin.MeetingJoinResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

import static com.dbs.club.domain.common.MeetingState.OPEN;

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


        if(meetingJoinRepository.existsByMemberAndMeeting(member, meeting)) {
            throw new MeetingJoinException(ErrorCode.MEETING_JOIN_DUPLICATE);
        }

        LocalDate meetingDate = meeting.getDate();
        if (meetingJoinRepository.existsByMemberAndMeetingDate(member, meetingDate)) {
            throw new MeetingJoinException(ErrorCode.MEETING_JOIN_DATE_DUPLICATE);
        }

        if(meeting.getStatus().isNotOpen()) {
            throw new MeetingJoinException(ErrorCode.MEETING_STATUS_NOT_OPEN);
        }

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
    public void cancelMeetingJoin(Long meetingJoinId, Long memberId) {
        MeetingJoin meetingJoin = getMeetingJoin(meetingJoinId);

        if (meetingJoin.isMeetingDateBeforeToday()) {
            throw new MeetingJoinException(ErrorCode.MEETING_JOIN_CAN_NOT_CANCEL);
        }

        if (!meetingJoin.getMember().getId().equals(memberId)) {
            throw new MeetingJoinException(ErrorCode.MEETING_JOIN_PERMISSION_DENIED);
        }

        MeetingJoinState meetingJoinOldStatus = meetingJoin.getStatus();
        MeetingState meetingOldStatus = meetingJoin.getMeeting().getStatus();

        meetingJoin.cancel();

        if (canUpdateMeetingStatus(meetingJoinOldStatus, meetingOldStatus)) {
            meetingJoin.getMeeting().updateStatus(OPEN);
        }

        meetingJoin.getMeeting().updateJoinCount(meetingJoin.getMeeting().getJoinCount() - DECREMENT_ONE);

        meetingJoinRepository.save(meetingJoin);
    }


    private boolean canUpdateMeetingStatus(MeetingJoinState meetingJoinOldStatus, MeetingState meetingOldStatus) {
        return MeetingJoinState.JOIN == meetingJoinOldStatus && MeetingState.FULL == meetingOldStatus;
    }

    public Page<MeetingJoinResponseDto.List> getMeetingJoin(@PathVariable Long memberId, int page) {
        Member member = memberService.getMember(memberId);
        Pageable pageable = PageRequest.of(page, 3);
        Page<MeetingJoin> meetingJoinPage = meetingJoinRepository.findByMemberId(member.getId(), pageable);
        return meetingJoinPage.map(meetingJoin -> {
            Meeting meeting = meetingJoin.getMeeting();
            return MeetingJoinResponseDto.List.fromEntity(meetingJoin, meeting);
        });
    }

}
