package com.dbs.club.presentation.meeting;

import com.dbs.club.domain.meeting.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ROOT_URL)
@RestController
public class MeetingController {

    static final String ROOT_URL = "/api/meetings";
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<Void> createMeeting() {
        Member member = memberService.getMemberById(create.getMemberId());

        Meeting meeting = create.toEntity(member);

        meetingService.saveMeeting(meeting);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
                .pathSegment("{meetingId}")
                .buildAndExpand(meeting.getId())
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }
}
