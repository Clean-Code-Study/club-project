package com.dbs.club.domain.board;

import com.dbs.club.domain.board.exception.BoardException;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.member.Member;
import com.dbs.club.domain.member.MemberService;
import com.dbs.club.infrastructure.board.BoardRepository;
import com.dbs.club.presentation.board.BoardRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    private static final List<String> PROFANITYWORDS = Arrays.asList("욕설1", "욕설2", "욕설3");

    public BoardService(BoardRepository boardRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.memberService = memberService;
    }

    private void checkProfanity(String text) {
        for (String profanity : PROFANITYWORDS) {
            if (text.contains(profanity)) {
                throw new BoardException(ErrorCode.PROFANITY_FOUND);
            }
        }
    }

    @Transactional
    public long createBoard(BoardRequestDto.Create create) {

        checkProfanity(create.title());
        checkProfanity(create.content());

        Member member = memberService.getMember(create.memberId());

        Board board = Board.builder()
                .member(member)
                .title(create.title())
                .content(create.content())
                .status(RegisterDeleteState.REGISTERED)
                .build();

        return boardRepository.save(board).getId();
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(ErrorCode.BOARD_NOT_FOUND));
            return board;
    }

    @Transactional
    public void updateBoard(BoardRequestDto.Update request, Long boardId) {

        checkProfanity(request.title());
        checkProfanity(request.content());

        Board board = getBoard(boardId);
        board.update(
                request.title(),
                request.content()
        );
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId){
        Board board = getBoard(boardId);

        board.delete();
    }
}
