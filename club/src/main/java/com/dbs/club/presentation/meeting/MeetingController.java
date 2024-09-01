package com.dbs.club.presentation.meeting;

import com.dbs.club.domain.meeting.MeetingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dbs.club.presentation.meeting.MeetingController.ROOT_URL;

@RequestMapping(ROOT_URL)
@RestController
public class MeetingController {

    static final String ROOT_URL = "/api/meetings";
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<Void> createMeeting(@RequestBody @Valid MeetingRequestDto.Create create) {

        long meetingId = meetingService.createMeeting(create);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
                .pathSegment("{meetingId}")
                .buildAndExpand(meetingId)
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping
    public ResponseEntity<Page<MeetingResponseDto.List>> getMeetings(@RequestParam(defaultValue = "0") int page) {
        Page<MeetingResponseDto.List> meetings = meetingService.getMeetings(page);
        return ResponseEntity.ok(meetings);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{meetingId}")
    public ResponseEntity<Void> updateMeeting(
            @PathVariable Long meetingId,
            @Valid @RequestBody MeetingRequestDto.Update request
    ) {
        meetingService.updateMeeting(request, meetingId);
        return ResponseEntity.noContent().build();
    }
}
