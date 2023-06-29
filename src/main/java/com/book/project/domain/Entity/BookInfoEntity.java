package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "bookinfo")
@Getter
@Setter
public class BookInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "poster", columnDefinition = "LONGTEXT", nullable = false)
    private String poster;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "summary", columnDefinition = "LONGTEXT", nullable = false)
    private String summary;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "writer", nullable = false)
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookinfo_idx") // 수정된 부분
    private FeedEntity feed;

    // Constructor
    public BookInfoEntity() {
    }

    // Constructor with all fields
    public BookInfoEntity(Long idx, String genre, String poster, LocalDate publicationDate, String publisher, String summary, String title, String writer, FeedEntity feed) {
        this.idx = idx;
        this.genre = genre;
        this.poster = poster;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.summary = summary;
        this.title = title;
        this.writer = writer;
        this.feed = feed;
    }
}


//Kakao OpenAPI 용 엔티티
//@Table(name = "bookinfo")
//public class BookInfoEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "idx", nullable = false)
//    private int idx;
//
//    @Column(name = "title", nullable = false)
//    private String title;
//
//    @Column(name = "authors", columnDefinition = "TEXT")
//    private String authors;
//
//    @Column(name = "contents", columnDefinition = "TEXT")
//    private String contents;
//
//    @Column(name = "datetime")
//    private String datetime;
//
//    @Column(name = "isbn", length = 255)
//    private String isbn;
//
//    @Column(name = "price")
//    private Integer price;
//
//    @Column(name = "publisher", length = 255)
//    private String publisher;
//
//    @Column(name = "sale_price")
//    private Integer salePrice;
//
//    @Column(name = "status", length = 255)
//    private String status;
//
//    @Column(name = "thumbnail", length = 255)
//    private String thumbnail;
//
//    @Column(name = "translators", columnDefinition = "JSON")
//    private String[] translators;
//
//    @Column(name = "url", length = 255)
//    private String url;
//
//    @Builder
//    public BookInfoEntity(int idx, String title, String authors, String contents, String datetime, String isbn, Integer price, String publisher, Integer salePrice, String status, String thumbnail, String[] translators, String url) {
//        this.idx = idx;
//        this.title = title;
//        this.authors = authors;
//        this.contents = contents;
//        this.datetime = datetime;
//        this.isbn = isbn;
//        this.price = price;
//        this.publisher = publisher;
//        this.salePrice = salePrice;
//        this.status = status;
//        this.thumbnail = thumbnail;
//        this.translators = translators;
//        this.url = url;
//    }
//}


