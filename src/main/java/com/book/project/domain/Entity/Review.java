package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookinfo_idx")
    private BookInfo bookInfo;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_idx")
    private Feed feed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_idx")
    private Member Member;

    // Constructors, getters, setters, and other methods
}
