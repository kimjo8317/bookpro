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
@Table(name = "subscribe")
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne(mappedBy = "sub")
    private Member member;

    private Boolean confirm;
    private LocalDateTime endDate;
    private LocalDateTime startDate;

    // 생성자, getter, setter, 기타 메서드

    @PostUpdate
    private void onPostUpdate() {
        if (idx == 1) {
            confirm = false;
        }
    }
}