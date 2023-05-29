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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Integer id;
    private String name;
    private String pw;
    private Integer likeIdx;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm")
    private Subscribe sub;

    // 트리거 코드 추가
    @Transient
    @Column(insertable = false, updatable = false)
    private Boolean subConfirm;

    // 생성자, getter, setter, 기타 메서드
}
