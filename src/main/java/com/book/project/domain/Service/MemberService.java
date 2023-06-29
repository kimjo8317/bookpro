package com.book.project.domain.Service;

import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity getMemberById(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));
        return memberEntity;
    }

    // 다른 메서드들과 추가적인 기능들...

}
