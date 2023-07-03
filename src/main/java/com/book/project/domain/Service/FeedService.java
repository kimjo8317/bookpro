package com.book.project.domain.Service;

import com.book.project.domain.DTO.FeedDTO;
import com.book.project.domain.Entity.FeedEntity;
import com.book.project.domain.Repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {
    private final FeedRepository feedRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public List<FeedDTO> getFeedData() {
        List<FeedEntity> feedEntities = feedRepository.findAll();
        return convertToDTOs(feedEntities);
    }

    private List<FeedDTO> convertToDTOs(List<FeedEntity> feedEntities) {
        List<FeedDTO> feedDTOs = new ArrayList<>();
        for (FeedEntity feedEntity : feedEntities) {
            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setIdx(feedEntity.getIdx());
            feedDTO.setContent(feedEntity.getContent());
            feedDTO.setCreateDate(feedEntity.getCreateDate());
            feedDTO.setWriter(feedEntity.getWriter());
            feedDTO.setBookinfoIdx(feedEntity.getBookInfoEntity().getIdx());
            feedDTO.setPoster(feedEntity.getBookInfoEntity().getPoster());
            feedDTOs.add(feedDTO);
        }
        return feedDTOs;
    }
}

