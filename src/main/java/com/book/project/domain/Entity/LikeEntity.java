package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "like_table")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_idx")
    private FeedEntity feedEntity;

    public LikeEntity() {
        // 기본 생성자
    }

    public static LikeEntity toLikeEntity(MemberEntity memberEntity, FeedEntity feedEntity){
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setMemberEntity(memberEntity);
        likeEntity.setFeedEntity(feedEntity);

        return likeEntity;
    }
}
