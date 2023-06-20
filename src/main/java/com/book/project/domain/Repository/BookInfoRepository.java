package com.book.project.domain.Repository;

import com.book.project.domain.Entity.BookInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookInfoRepository extends JpaRepository<BookInfoEntity, Long> {
    BookInfoEntity save(BookInfoEntity entity);

    //제목으로 검색
    List<BookInfoEntity> findByTitleContaining(String title);

}
