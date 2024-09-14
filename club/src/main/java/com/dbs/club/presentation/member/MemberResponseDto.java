package com.dbs.club.presentation.member;

import java.time.LocalDate;

import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;

public class MemberResponseDto {

    public record Detail(
        String loginId,
        String name,
        String contact,
        String nickname,
        LocalDate birth,
        MemberGenderType gender,
        String interest
    ) {
        public static Detail fromEntity(Member member) {
            return new Detail(
                member.getLoginId(),
                member.getName(),
                member.getContact(),
                member.getNickname(),
                member.getBirth(),
                member.getGender(),
                member.getInterest()
            );
        }
    }
}