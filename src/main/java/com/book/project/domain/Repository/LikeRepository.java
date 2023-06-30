package com.book.project.domain.Repository;

import com.book.project.domain.Entity.FeedEntity;
import com.book.project.domain.Entity.LikeEntity;
import com.book.project.domain.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntity findFirstByMemberEntityAndFeedEntity(MemberEntity memberEntity, FeedEntity feedEntity);

}
