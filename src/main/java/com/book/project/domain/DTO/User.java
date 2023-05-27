package com.book.project.domain.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class User {
    private Long id;// 데이터 ID
    @NotEmpty
    private String loginId; //로그인 ID
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
}