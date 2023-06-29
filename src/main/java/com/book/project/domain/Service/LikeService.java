package com.book.project.domain.Service;

import com.book.project.domain.Entity.FeedEntity;
import com.book.project.domain.Entity.LikeEntity;
import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Repository.FeedRepository;
import com.book.project.domain.Repository.LikeRepository;
import com.book.project.domain.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, MemberRepository memberRepository, FeedRepository feedRepository) {
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.feedRepository = feedRepository;
    }

    @Transactional
    public void addLike(Long memberId, Long feedId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        FeedEntity feedEntity = feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("Feed not found"));

        LikeEntity likeEntity = LikeEntity.toLikeEntity(memberEntity, feedEntity);
        likeRepository.save(likeEntity);

        feedEntity.getLikes().add(likeEntity); // 추가된 코드: 좋아요를 피드에 추가
    }
}