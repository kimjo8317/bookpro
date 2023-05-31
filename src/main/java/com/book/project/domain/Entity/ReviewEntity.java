package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx",nullable = false)
    private Long idx;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookinfo_idx")
    private List<BookInfoEntity> bookInfoEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_idx")
    private FeedEntity feedEntity;

    // Getter and Setter methods
}