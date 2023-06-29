package com.book.project.domain.Repository;

import com.book.project.domain.Entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
}
