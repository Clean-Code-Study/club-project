package com.dbs.club.infrastructure.meetingjoin;
import com.dbs.club.domain.meetingjoin.MeetingJoin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class MeetingJoinRepositoryTest {

    @Autowired
    private MeetingJoinRepository meetingJoinRepository;

    @Test
    void saveTest() {

        MeetingJoin saveMeetingJoin = MeetingJoin.builder().build();

        MeetingJoin meetingJoin = meetingJoinRepository.save(saveMeetingJoin);
        assertThat(meetingJoin.getId()).isNotNull();
    }
}
