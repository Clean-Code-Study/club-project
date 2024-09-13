package com.dbs.club.infrastructure.meeting;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import com.dbs.club.domain.member.MemberStatus;

@Transactional
@SpringBootTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void saveTest() {
        Member member = new Member("testId", "1234", "testName", "01011112222",
                "testNickname", LocalDate.of(2000, 1, 1), MemberGenderType.FEMALE,
                "testInterest", MemberStatus.REGISTERED);

        Meeting saveMeeting = Meeting.builder()
                .member(member)
                .title("title")
                .content("content")
                .location("location")
                .date(LocalDate.of(2000, 1, 1))
                .joinLimit(1)
                .joinCount(0)
                .status(MeetingState.OPEN)
                .build();

        Meeting meeting = meetingRepository.save(saveMeeting);
        assertThat(meeting.getId()).isNotNull();
    }


}
