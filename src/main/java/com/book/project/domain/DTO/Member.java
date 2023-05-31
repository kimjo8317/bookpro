package com.book.project.domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long idx;
    private Integer id;
    private Integer likeIdx;
    private String name;
    private String pw;
    private Integer confirm;
}
