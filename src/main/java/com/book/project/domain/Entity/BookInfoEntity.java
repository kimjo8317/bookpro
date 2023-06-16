package com.book.project.domain.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookinfo")
public class BookInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private int idx;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", columnDefinition = "TEXT")
    private String authors;

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name = "datetime")
    private String datetime;

    @Column(name = "isbn", length = 255)
    private String isbn;

    @Column(name = "price")
    private Integer price;

    @Column(name = "publisher", length = 255)
    private String publisher;

    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "thumbnail", length = 255)
    private String thumbnail;

    @Column(name = "translators", columnDefinition = "JSON")
    private String[] translators;

    @Column(name = "url", length = 255)
    private String url;

    @Builder
    public BookInfoEntity(int idx, String title, String authors, String contents, String datetime, String isbn, Integer price, String publisher, Integer salePrice, String status, String thumbnail, String[] translators, String url) {
        this.idx = idx;
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.salePrice = salePrice;
        this.status = status;
        this.thumbnail = thumbnail;
        this.translators = translators;
        this.url = url;
    }

    // Add getters and setters, constructors, and other necessary methods
}
