package com.dbs.club.domain.meeting;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.infrastructure.meeting.MeetingRepository;
import org.springframework.stereotype.Service;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.presentation.meeting.MeetingRequestDto;

import java.time.LocalDate;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

     public long createMeeting(MeetingRequestDto.Create create) {
        Meeting meeting = create.toEntity();
        return meetingRepository.save(meeting).getId();
    }
}
