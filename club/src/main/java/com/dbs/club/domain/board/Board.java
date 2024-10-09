package com.dbs.club.domain.board;

import com.dbs.club.domain.board.exception.BoardException;
import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.common.exception.ErrorCode;
import com.dbs.club.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Size(max = 1500, message = "내용은 최대 1500자까지 가능합니다.")
    @Column(name = "content", nullable = false, length = 1500)
    private String content;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private RegisterDeleteState status;

    private static final List<String> PROFANITYWORDS = Arrays.asList("욕설1", "욕설2", "욕설3");

    public void checkProfanity() {
        for (String profanity : PROFANITYWORDS) {
            if (this.title.contains(profanity) || this.content.contains(profanity)) {
                throw new BoardException(ErrorCode.BOARD_PROFANITY_FOUND);
            }
        }
    }

    public void update(
            String title,
            String content
    ){
        this.title = title;
        this.content = content;
        checkProfanity();

    }

    public void delete() {
        this.status = RegisterDeleteState.DELETED;
    }
}
