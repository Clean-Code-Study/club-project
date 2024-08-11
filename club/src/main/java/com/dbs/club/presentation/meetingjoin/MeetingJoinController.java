package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.domain.meetingjoin.MeetingJoinService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dbs.club.presentation.meetingjoin.MeetingJoinController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class MeetingJoinController {

    static final String ROOT_URL = "/api/meeting-joins";
    private final MeetingJoinService meetingJoinService;

    public MeetingJoinController(MeetingJoinService meetingJoinService) {
        this.meetingJoinService = meetingJoinService;
    }

    @PostMapping
    public ResponseEntity<Void> createMeetingJoin(@RequestBody @Valid MeetingJoinRequestDto.Create create) {
        long meetingJoinId = meetingJoinService.createMeetingJoin(create);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
                .pathSegment("{meetingJoinId}").buildAndExpand(meetingJoinId).toUri();

        return ResponseEntity.created(locationUri).build();
    }
}
