package com.dbs.club.infrastructure.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.club.domain.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByMemberId(Long memberId);
}
