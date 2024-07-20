package com.dbs.club.domain.member;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.infrastructure.member.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember() {
        Member member = Member.init("testId", "1234", "testName", "01011112222",
            "testNickname", LocalDate.of(2000,1,1), MemberGenderType.FEMALE,
            "testInterest", RegisterDeleteState.REGISTERED);
        return memberRepository.save(member);
    }
}
