package com.book.project.domain.Controller;

import com.book.project.domain.DTO.LikeRequestDto;
import com.book.project.domain.Service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLikeToFeed(@RequestBody LikeRequestDto requestDto) {
        likeService.addLike(requestDto.getMemberId(), requestDto.getFeedId());
    }
}
