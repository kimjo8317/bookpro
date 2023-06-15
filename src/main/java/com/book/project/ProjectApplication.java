package com.book.project;

import com.book.project.domain.Service.KakaoBookSearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling

	public class ProjectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProjectApplication.class, args);

		// KakaoBookSearchService 인스턴스 생성
		KakaoBookSearchService kakaoBookSearchService = context.getBean(KakaoBookSearchService.class);

		try {
			kakaoBookSearchService.searchAndSaveBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
