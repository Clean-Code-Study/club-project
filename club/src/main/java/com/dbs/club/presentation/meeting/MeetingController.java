package com.dbs.club.presentation.meeting;

import com.dbs.club.domain.meeting.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/meetings")
@RestController
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<Long> saveMeeting() {
        return ResponseEntity.ok(meetingService.saveMeeting().getId());
    }
}
