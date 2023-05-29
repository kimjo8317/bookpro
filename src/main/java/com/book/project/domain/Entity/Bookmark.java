package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_idx")
    private BookInfo bookinfo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // Constructors, getters, setters, and other methods
}
