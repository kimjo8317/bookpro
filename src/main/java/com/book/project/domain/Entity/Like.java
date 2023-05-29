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
@Table(name = "`like`")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_idx")
    private Feed feed;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // Constructors, getters, setters, and other methods
}
