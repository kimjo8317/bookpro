package com.book.project.domain.Repository;

import com.book.project.domain.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserRepository extends JpaRepository<MemberEntity, Integer> {

    MemberEntity save(MemberEntity member);
    MemberEntity findById(String id);
    boolean existsById(String id);
}
