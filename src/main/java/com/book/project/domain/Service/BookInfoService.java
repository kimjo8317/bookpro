package com.book.project.domain.Service;

import com.book.project.domain.Entity.BookInfoEntity;
import com.book.project.domain.Repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookInfoService {

    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public BookInfoService(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    public List<BookInfoEntity> getAllBookInfo() {
        return bookInfoRepository.findAll();
    }

    public Optional<BookInfoEntity> getRandomBookInfo() {
        List<BookInfoEntity> bookInfoList = bookInfoRepository.findAll();

        if (bookInfoList.isEmpty()) {
            return Optional.empty();
        }

        Random random = new Random();
        int randomBookInfoIndex = random.nextInt(bookInfoList.size());
        BookInfoEntity randomBookInfo = bookInfoList.get(randomBookInfoIndex);

        return Optional.of(randomBookInfo);
    }

    public Optional<BookInfoEntity> getBookInfoByBookInfoIdx(Long bookInfoIdx) {
        return bookInfoRepository.findById(bookInfoIdx);
    }
}
