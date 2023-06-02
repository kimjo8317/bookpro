package com.book.project.domain.Repository;


import com.book.project.domain.Entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    List<SubscribeEntity> findByEndDateLessThanEqual(LocalDateTime currentDate);
    SubscribeEntity findByMemberIdx(Long memberId);

    SubscribeEntity save(SubscribeEntity subscribeEntity);
}