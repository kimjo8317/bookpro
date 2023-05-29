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
@Table(name = "feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookinfo_idx")
    private BookInfo bookInfo;

    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    private Integer likes;
    private String title;
    private Integer views;
    private String writer;

    // Constructors, getters, setters, and other methods
}
