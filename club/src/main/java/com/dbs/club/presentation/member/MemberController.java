package com.dbs.club.presentation.member;

import static com.dbs.club.presentation.member.MemberController.*;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dbs.club.domain.member.MemberService;

@RequestMapping(ROOT_URL)
@RestController
public class MemberController {

    static final String ROOT_URL = "/api/members";
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequestDto.Create create) {
        long memberId = memberService.createMember(create);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
            .pathSegment("{memberId}")
            .buildAndExpand(memberId)
            .toUri();

        return ResponseEntity.created(locationUri).build();
    }
}
