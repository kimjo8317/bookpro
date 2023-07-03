package com.book.project.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedDTO {
    private Long idx;
    private String content;
    private LocalDateTime createDate;
    private String writer;
    private Long bookinfoIdx;
    private String poster;



    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getBookinfoIdx() {
        return bookinfoIdx;
    }

    public void setBookinfoIdx(Long bookinfoIdx) {
        this.bookinfoIdx = bookinfoIdx;
    }
}
