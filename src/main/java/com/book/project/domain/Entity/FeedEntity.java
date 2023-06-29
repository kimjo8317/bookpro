package com.book.project.domain.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@Setter
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "writer", length = 255)
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookinfo_idx", referencedColumnName = "idx")
    private BookInfoEntity bookInfoEntity;

    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.ALL)
    private List<LikeEntity> likes;
}
