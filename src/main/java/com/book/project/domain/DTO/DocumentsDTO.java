package com.book.project.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DocumentsDTO {
    private String url;
    private List<String> translators;
    private String title;
    private String thumbnail;
    private String status;
    private int salePrice;
    private String publisher;
    private int price;
    private String isbn;
    private String datetime;
    private String contents;
    private List<String> authors;

    @Builder
    public DocumentsDTO(String url, List<String> translators, String title, String thumbnail, String status, int salePrice, String publisher, int price, String isbn, String datetime, String contents, List<String> authors) {
        this.url = url;
        this.translators = translators;
        this.title = title;
        this.thumbnail = thumbnail;
        this.status = status;
        this.salePrice = salePrice;
        this.publisher = publisher;
        this.price = price;
        this.isbn = isbn;
        this.datetime = datetime;
        this.contents = contents;
        this.authors = authors;
    }

    public DocumentsDTO(){

    }
}
