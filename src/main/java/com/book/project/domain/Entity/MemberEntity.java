package com.book.project.domain.Entity;

import com.book.project.domain.DTO.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "member")
public class MemberEntity extends Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx",nullable = false)
    private Long idx;

    @Column(name="id")
    private String id;

    @Column(name="likeIdx")
    private Integer likeIdx;

    @Column(name="name")
    private String name;

    @Column(name="pw")
    private String pw;

    @Column(name="confirm")
    private Integer confirm;


}