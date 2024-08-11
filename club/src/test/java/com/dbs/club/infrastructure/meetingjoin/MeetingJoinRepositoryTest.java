package com.dbs.club.infrastructure.meetingjoin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class MeetingJoinRepositoryTest {

    @Autowired
    private MeetingJoinRepository meetingJoinRepository;

    @Test
    void saveTest() {
//        Member member = Member.init("testId", "1234", "testName", "01011112222",
//                "testNickname", LocalDate.of(2000, 1, 1), MemberGenderType.FEMALE,
//                "testInterest", RegisterDeleteState.REGISTERED);

//        Meeting meeting = Meeting.init(member, "testTitle", "testContent", "testLocation", LocalDate.of(2000,1,1), 100, 0, MeetingState.OPEN);
//        MeetingJoin saveMeetingJoin = MeetingJoin.init(member, meeting, RegisterDeleteState.REGISTERED);
//
//        MeetingJoin meetingJoin = meetingJoinRepository.save(saveMeetingJoin);
//        assertThat(meetingJoin.getId()).isNotNull();
    }
}
