package com.dbs.club.domain.member;

import org.springframework.stereotype.Service;

import com.dbs.club.infrastructure.member.MemberRepository;
import com.dbs.club.presentation.member.MemberRequestDto;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public long createMember(MemberRequestDto.Create create) {
        Member member = create.toEntity();
        return memberRepository.save(member).getId();
    }
}
