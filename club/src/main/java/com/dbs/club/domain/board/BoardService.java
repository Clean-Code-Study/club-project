package com.dbs.club.domain.board;

import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.board.BoardRepository;
import com.dbs.club.presentation.board.BoardRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public BoardService(BoardRepository boardRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.memberService = memberService;
    }

    @Transactional
    public long createBoard(BoardRequestDto.Create create) {

        Member member = memberService.getMember(create.memberId());

        Board board = Board.builder()
                .member(member)
                .title(create.title())
                .content(create.content())
                .status(RegisterDeleteState.REGISTERED)
                .build();

        return boardRepository.save(board).getId();
    }
}
