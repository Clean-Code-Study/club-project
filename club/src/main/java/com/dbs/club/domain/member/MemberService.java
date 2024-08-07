package com.dbs.club.domain.member;

import java.time.LocalDate;

import com.dbs.club.domain.member.exception.MemberException;
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

    public Member getMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberException("회원을 찾을 수 없습니다."));
        return findMember;
    }

}
