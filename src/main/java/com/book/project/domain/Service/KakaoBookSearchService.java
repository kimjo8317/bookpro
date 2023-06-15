package com.book.project.domain.Service;

import com.book.project.domain.Entity.BookInfoEntity;
import com.book.project.domain.Repository.BookInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Service
public class KakaoBookSearchService {
    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public KakaoBookSearchService(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

        public void searchAndSaveBooks() throws URISyntaxException {

            String url = "https://dapi.kakao.com/v3/search/book";
            String target = "title";
            String query = "소설";
            String authorization = "KakaoAK " + "91b01b93a0adaba89cc63cf99c22f3a4";
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

            // RestTemplate을 사용하여 HTTP 요청을 보낼 준비
            RestTemplate restTemplate = new RestTemplate();

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            // HTTP GET 요청 생성 (쿼리 파라미터 추가)
            URI uri = new URI(url + "?target=" + target + "&query=" + encodedQuery);
            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

            // HTTP 요청을 보내고 응답을 받음
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

            // 응답 데이터 가져오기
            String responseJson = responseEntity.getBody();

            try {
                // 응답 데이터를 DTO에 매핑하기 위해 ObjectMapper 사용
                ObjectMapper objectMapper = new ObjectMapper();
                BookInfoEntity[] bookInfoEntities = objectMapper.readValue(responseJson, BookInfoEntity[].class);

                // List<BookInfoEntity>로 변환
                List<BookInfoEntity> bookInfoList = Arrays.asList(bookInfoEntities);

                // BookInfoEntity 저장
                for (BookInfoEntity bookInfoEntity : bookInfoEntities) {
                    bookInfoRepository.save(bookInfoEntity);
                }

                // 필요한 작업을 수행하도록 bookInfoDTO 객체를 처리합니다.
            } catch (IOException e) {
                // 예외 처리
                System.out.println("Error reading JSON: " + e.getMessage());
            }
        }

}