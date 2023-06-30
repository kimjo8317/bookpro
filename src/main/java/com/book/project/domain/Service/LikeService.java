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

        LikeEntity existingLike = likeRepository.findFirstByMemberEntityAndFeedEntity(memberEntity, feedEntity);

        if (existingLike != null) {
            // 이미 좋아요한 경우 좋아요 제거
            likeRepository.delete(existingLike);
            feedEntity.getLikes().remove(existingLike);
        } else {
            // 좋아요 추가
            LikeEntity likeEntity = LikeEntity.toLikeEntity(memberEntity, feedEntity);
            likeRepository.save(likeEntity);
            feedEntity.getLikes().add(likeEntity);
        }
    }
}
