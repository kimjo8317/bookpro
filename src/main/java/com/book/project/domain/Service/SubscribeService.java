package com.book.project.domain.Service;

import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Entity.SubscribeEntity;
import com.book.project.domain.Repository.SubscribeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public SubscribeService(SubscribeRepository subscribeRepository) {
        this.subscribeRepository = subscribeRepository;
    }

    public SubscribeEntity saveSubscription(SubscribeEntity subscribeEntity) {


        // 회원 테이블의 confirm 값을 업데이트
        MemberEntity member = subscribeEntity.getMember();
        member.setConfirm(subscribeEntity.getConfirm());

        return subscribeRepository.save(subscribeEntity);
    }

    public List<SubscribeEntity> getExpiredSubscriptions(LocalDateTime currentDate) {
        return subscribeRepository.findByEndDateLessThanEqual(currentDate);
    }

    public SubscribeEntity getSubscriptionByMemberId(Long memberId) {
        return subscribeRepository.findByMemberIdx(memberId);
    }
}