package com.book.project.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class KakaoBookSearch {

    public static void main(String[] args) throws URISyntaxException {
        String url = "https://dapi.kakao.com/v3/search/book";
        String target = "title";
        String query = "시집";
        String authorization = "KakaoAK 91b01b93a0adaba89cc63cf99c22f3a4";

        // RestTemplate을 사용하여 HTTP 요청을 보낼 준비
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP GET 요청 생성
        URI uri = new URI(url + "?target=" + target + "&query=" + query);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

        // HTTP 요청을 보내고 응답을 받음
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // 응답 데이터 가져오기
        String responseJson = responseEntity.getBody();

        // 데이터 처리
        // ...

        System.out.println(responseJson);
    }
}
