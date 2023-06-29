package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "member")
public class MemberEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx",nullable = false)
    private Long idx;

    @Column(name="id")
    private String id;

    @Column(name="like_table_Idx")
    private Integer likeIdx;

    @Column(name="name")
    private String name;

    @Column(name="pw")
    private String pw;

    @Column(name="confirm")
    private Boolean confirm;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private SubscribeEntity subscribe;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likes = new ArrayList<>();

    public boolean isConfirm() {
        return Boolean.TRUE.equals(confirm);
    }

    //구독정보가 존재하지 않아도 콘솔에 노출
    public SubscribeEntity getSubscribe() {
        if (this.subscribe == null) {
            return SubscribeEntity.builder().build();
        }
        return this.subscribe;
    }
    public void addLike(LikeEntity like) {
        likes.add(like);
        like.setMemberEntity(this);
    }

    public void removeLike(LikeEntity like) {
        likes.remove(like);
        like.setMemberEntity(null);
    }
}