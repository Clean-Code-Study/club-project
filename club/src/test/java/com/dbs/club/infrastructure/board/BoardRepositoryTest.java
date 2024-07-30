package com.dbs.club.infrastructure.board;

import com.dbs.club.domain.board.Board;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberGenderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void saveTest() {
        Member member = Member.init("testId", "1234", "testName", "01011112222",
                "testNickname", LocalDate.of(2000,1,1), MemberGenderType.FEMALE,
                "testInterest", RegisterDeleteState.REGISTERED);

        Board  saveBoard = Board.init(member,"제목","내용",RegisterDeleteState.REGISTERED);

        Board board = boardRepository.save(saveBoard);
        assertThat(board.getId()).isNotNull();

    }



}
