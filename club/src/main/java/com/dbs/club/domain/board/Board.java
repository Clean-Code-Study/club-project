package com.dbs.club.domain.board;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 1500)
    private String content;

    @Column(name = "status", nullable = false, length = 30)
    private RegisterDeleteState status;

    //생성자
    public Board(Member member, String title, String content, RegisterDeleteState status) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public static Board init(Member member, String title, String content, RegisterDeleteState status) {
        return new Board(member, title, content, status);
    }
}
