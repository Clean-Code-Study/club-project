package com.dbs.club.infrastructure.member;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.domain.member.MemberStatus;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void saveTest() {
        Member saveMember = new Member("testId", "1234", "testName", "01011112222",
            "testNickname", LocalDate.of(2000,1,1), MemberGenderType.FEMALE,
            "testInterest", MemberStatus.REGISTERED);

        Member member = memberRepository.save(saveMember);
        assertThat(member.getId()).isNotNull();
    }

}
