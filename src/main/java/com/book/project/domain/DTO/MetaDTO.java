package com.book.project.domain.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaDTO {
    private int totalCount;
    private int pageableCount;
    private boolean isEnd;

    @Builder
    public MetaDTO(int totalCount, int pageableCount, boolean isEnd) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.isEnd = isEnd;
    }
    public MetaDTO(){

    }
}
