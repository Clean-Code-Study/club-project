package com.dbs.club.infrastructure.meetingjoin;

import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.meetingjoin.MeetingJoin;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class MeetingRepositoryTest {

    @Autowired
    private MeetingJoinRepository meetingJoinRepository;

    @Test
    void saveTest() {
        Member member = Member.init("testId", "1234", "testName", "01011112222",
                "testNickname", LocalDate.of(2000,1,1), MemberGenderType.FEMALE,
                "testInterest", RegisterDeleteState.REGISTERED);


        MeetingJoin saveMeetingJoin = MeetingJoin.init(member, RegisterDeleteState.REGISTERED);

        MeetingJoin meetingJoin = meetingJoinRepository.save(saveMeetingJoin);
        assertThat(meetingJoin.getId()).isNotNull();
    }
}
