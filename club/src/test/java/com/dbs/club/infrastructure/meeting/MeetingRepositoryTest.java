package com.dbs.club.infrastructure.meeting;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void saveTest() {
        Member member = Member.init("testId", "1234", "testName", "01011112222",
                "testNickname", LocalDate.of(2000,1,1), MemberGenderType.FEMALE,
                "testInterest", RegisterDeleteState.REGISTERED);
        Meeting saveMeeting = Meeting.init(member, "testTitle", "testContent", "testLocation",
                LocalDate.of(2000,1,1), 100, 0, MeetingState.OPEN);

        Meeting meeting = meetingRepository.save(saveMeeting);
        assertThat(meeting.getId()).isNotNull();
    }

}
