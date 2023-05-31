package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "like")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx",nullable = false)
    private Long idx;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_idx")
    private FeedEntity feedEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private MemberEntity member;

    // Constructors, getters, and setters
}