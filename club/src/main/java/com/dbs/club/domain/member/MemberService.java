package com.dbs.club.domain.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.member.exception.MemberException;
import com.dbs.club.infrastructure.member.MemberRepository;
import com.dbs.club.presentation.member.MemberRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public long createMember(MemberRequestDto.Create create) {
        Member member = create.toEntity();
        validateDuplicateMember(member);

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

    @Transactional
    public void updateMember(MemberRequestDto.Update request, Long memberId) {
        Member member = getMember(memberId);
        duplicateNicknameCheck(request.nickname());

        member.update(
            request.password(),
            request.name(),
            request.contact(),
            request.nickname(),
            request.birth(),
            request.interest()
        );
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = getMember(memberId);

        member.delete();
    }

    private void validateDuplicateMember(Member member) {
        duplicateLoginIdCheck(member.getLoginId());
        duplicateNicknameCheck(member.getNickname());
    }

    private void duplicateLoginIdCheck(String loginId) {
        memberRepository.findByLoginId(loginId)
            .ifPresent(m -> {
                throw new MemberException(ErrorCode.MEMBER_LOGIN_ID_DUPLICATE);
            });
    }

    private void duplicateNicknameCheck(String nickname) {
        memberRepository.findByNickname(nickname)
            .ifPresent(m -> {
                throw new MemberException(ErrorCode.MEMBER_NICKNAME_DUPLICATE);
            });
    }
}
