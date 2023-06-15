package com.book.project.domain.Repository;

import com.book.project.domain.Entity.BookInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookInfoRepository extends JpaRepository<BookInfoEntity, Long> {
    BookInfoEntity save(BookInfoEntity entity);
}
