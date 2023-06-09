package com.book.project.domain.Controller;

import com.book.project.domain.Entity.BookInfoEntity;
import com.book.project.domain.Repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //db에 담긴 책정보 조회
    @GetMapping("/books")
    public List<BookInfoEntity> getAllBooks() {
        return bookInfoRepository.findAll();
    }

    @GetMapping("/books/search")
    public List<BookInfoEntity> searchBooksByTitle(@RequestParam("title") String title) {
        return bookInfoRepository.findByTitleContaining(title);
    }
}
