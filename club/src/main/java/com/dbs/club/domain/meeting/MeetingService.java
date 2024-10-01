package com.dbs.club.domain.meeting;


import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.meeting.exception.MeetingException;
import com.dbs.club.domain.meetingjoin.MeetingJoinState;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.meeting.MeetingRepository;
import com.dbs.club.presentation.meeting.MeetingRequestDto;
import com.dbs.club.presentation.meeting.MeetingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public Meeting getMeeting(Long meetingId) {
        Meeting findMeeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new MeetingException(ErrorCode.MEETING_NOT_FOUND));
        return findMeeting;
    }

    public Page<MeetingResponseDto.List> getMeetings(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Meeting> meetings = meetingRepository.findAll(pageable);

        return meetings.map(MeetingResponseDto.List::fromEntity);
    }

    @Transactional
    public void updateMeeting(MeetingRequestDto.Update request, Long meetingId, Long memberId) {
        Meeting meeting = getMeeting(meetingId);

        if (!meeting.isCreator(memberId)) {
            throw new MeetingException(ErrorCode.MEETING_CAN_NOT_UPDATE);
        }

        if (meeting.canNotUpdateMeetingDate()) {
           throw new MeetingException(ErrorCode.MEETING_CAN_NOT_UPDATE);
        }

        meeting.update(
                request.title(),
                request.content(),
                request.location(),
                request.date(),
                request.joinLimit()
        );
    }

    @Transactional
    public void deleteMeeting(Long meetingId, Long memberId) {
        Meeting meeting = getMeeting(meetingId);

        if (!meeting.isCreator(memberId)) {
            throw new MeetingException(ErrorCode.MEETING_CAN_NOT_DELETE);
        }

        meeting.getMeetingJoins().forEach(join -> join.updateStatus(MeetingJoinState.CANCEL));

        meeting.delete();
    }
}
