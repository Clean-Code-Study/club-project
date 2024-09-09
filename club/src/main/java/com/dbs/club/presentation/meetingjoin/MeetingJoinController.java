package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.domain.meetingjoin.MeetingJoinService;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dbs.club.presentation.meetingjoin.MeetingJoinController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class MeetingJoinController {

    static final String ROOT_URL = "/api/meeting-joins";
    private final MeetingJoinService meetingJoinService;
    private final MemberService memberService;

    public MeetingJoinController(MeetingJoinService meetingJoinService, MemberService memberService) {
        this.meetingJoinService = meetingJoinService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMeetingJoin(@RequestBody @Valid MeetingJoinRequestDto.Create create) {
        long meetingJoinId = meetingJoinService.createMeetingJoin(create);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
                .pathSegment("{meetingJoinId}").buildAndExpand(meetingJoinId).toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{meetingJoinId}")
    public ResponseEntity<Void> cancelMeeting(@PathVariable Long meetingJoinId) {
        meetingJoinService.cancelMeetingJoin(meetingJoinId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Page<MeetingJoinResponseDto.List>> getMeetingJoin(@PathVariable Long memberId,@RequestParam(defaultValue = "0") int page) {
        Member member = memberService.getMember(memberId);
        Page<MeetingJoinResponseDto.List> meetingJoin = meetingJoinService.getMeetingJoin(member.getId(), page);
        return ResponseEntity.ok(meetingJoin);
    }

}
