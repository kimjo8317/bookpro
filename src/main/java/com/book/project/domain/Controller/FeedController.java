package com.book.project.domain.Controller;

import com.book.project.domain.DTO.FeedDTO;
import com.book.project.domain.Entity.FeedEntity;
import com.book.project.domain.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedController {
    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public List<FeedDTO> getFeed() {
        return feedService.getFeedData();
    }
}
