package com.dbs.club.infrastructure.board;

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
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void saveTest() {
        Member member = new Member("testId", "1234", "testName", "01011112222",
                "testNickname", LocalDate.of(2000, 1, 1), MemberGenderType.FEMALE,
                "testInterest", MemberStatus.REGISTERED);

//        Board saveBoard = Board.init(member, "제목", "내용", RegisterDeleteState.REGISTERED);

//        Board board = boardRepository.save(saveBoard);
//        assertThat(board.getId()).isNotNull();
    }
}
