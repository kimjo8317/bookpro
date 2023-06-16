package com.book.project.domain.Service;

import com.book.project.domain.DTO.BookInfoDTO;
import com.book.project.domain.DTO.DocumentsDTO;
import com.book.project.domain.Entity.BookInfoEntity;
import com.book.project.domain.Repository.BookInfoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
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

@Component
@Service
@Slf4j
public class KakaoBookSearchService {
    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public KakaoBookSearchService(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

        public void searchAndSaveBooks() throws URISyntaxException {

            String url = "https://dapi.kakao.com/v3/search/book";
            String target = "title";
            String query = "영화";
            String authorization = "KakaoAK " + "91b01b93a0adaba89cc63cf99c22f3a4";
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

            // RestTemplate을 사용하여 HTTP 요청을 보낼 준비
            RestTemplate restTemplate = new RestTemplate();

            // HTTP 요청 헤더 설정ㅣ
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            // HTTP GET 요청 생성 (쿼리 파라미터 추가)
            URI uri = new URI(url + "?target=" + target + "&query=" + encodedQuery + size );
            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

            // HTTP 요청을 보내고 응답을 받음
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

            // 응답 데이터 가져오기
            String responseJson = responseEntity.getBody();

            try {
                // 응답 데이터를 DTO에 매핑하기 위해 ObjectMapper 사용
                ObjectMapper objectMapper = new ObjectMapper();
                //JavaTimeModule 쓰는 이유 , 매핑 하다가 어떤 제공되지 않는 오류가 발생해서 사용함
                objectMapper.registerModule(new JavaTimeModule());
                //매핑에 필요한 기본 세팅중에 선언한 필드만 매핑 되도록 설정
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //선언한 필드만 매핑

                //여기에서 실제로 매핑이 이뤄짐 readValue를 사용하면 responseJson으로부터 데이터를 읽어서 BookInfoDTO 클래스로 변환과정을 거침
                BookInfoDTO bookInfoDTO = objectMapper.readValue(responseJson, BookInfoDTO.class);

                //받아온 내용 안에 documents 가 리스트 형식이므로 여러개니까 for문을 사용해서 매번 builder 과정을 거치게함
                for(DocumentsDTO item : bookInfoDTO.getDocuments()){
                    //데이터가 20개인 경우 1번부터 20번까지 for문을 돌면서 bookInfo (Entity)에 빌더를 이용해서 데이터를 만들어줌
                    BookInfoEntity bookInfo = BookInfoEntity.builder()
                            .price(item.getPrice())

                            .isbn(item.getIsbn())
                            .datetime(String.valueOf(item.getDatetime()))
                            .title(item.getTitle())
                            .url(item.getUrl())
                            .contents(item.getContents()).build();

                    //가공된 데이터를 저장함. for 1번인 경우 1번 데이터 저장하고 2번~~~ 20번 반복하므로 20개가 저장됨
                    bookInfoRepository.save(bookInfo);
                }
                




                // BookInfoEntity 저장
                //bookInfoRepository.save(bookInfo);

                // 필요한 작업을 수행하도록 bookInfoDTO 객체를 처리합니다.
            } catch (IOException e) {
                // 예외 처리
                System.out.println("Error reading JSON: " + e.getMessage());
            }
        }

}