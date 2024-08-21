package com.dbs.club.presentation.member;

import static com.dbs.club.presentation.member.MemberController.*;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dbs.club.domain.member.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(ROOT_URL)
@RestController
public class MemberController {

    static final String ROOT_URL = "/api/members";
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequestDto.Create request) {
        long memberId = memberService.createMember(request);

        URI locationUri = UriComponentsBuilder.fromPath(ROOT_URL)
            .pathSegment("{memberId}")
            .buildAndExpand(memberId)
            .toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(
        @PathVariable Long memberId,
        @Valid @RequestBody MemberRequestDto.Update request
    ) {
        memberService.updateMember(request, memberId);
        return ResponseEntity.noContent().build();
    }
}
