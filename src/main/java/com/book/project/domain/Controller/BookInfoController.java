package com.book.project.domain.Controller;

import com.book.project.domain.Entity.BookInfoEntity;
import com.book.project.domain.Repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/bookinfo")
@RestController
public class BookInfoController {

    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public BookInfoController(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @GetMapping("/books")
    public List<BookInfoEntity> getAllBooks() {
        return bookInfoRepository.findAll();
    }
}
