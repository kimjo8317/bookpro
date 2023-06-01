package com.book.project.domain.Controller;

import com.book.project.domain.DTO.LoginRequest;
import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Service.UserService;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.security.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final byte[] keyBytes;

    public UserController(UserService userService) {
        this.userService = userService;
        this.keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = userService.authenticateUser(loginRequest.getId(), loginRequest.getPw());
            if (isAuthenticated) {
                // 인증 성공 - JWT 발급
                String token = generateJwtToken(loginRequest.getId());

                // JWT 디버깅
                debugJwtToken(token);
                debugEncodeJwtToken(token);

                ObjectMapper objectMapper = new ObjectMapper();
                String responseJson = objectMapper.createObjectNode()
                        .put("token", token)
                        .put("message", "Login successful")
                        .toString();

                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(responseJson);
            } else {
                // 인증 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (IllegalArgumentException e) {
            // 예외 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String generateJwtToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        // 만료 시간 설정 (예: 1시간 후 만료)
        Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1시간(60분 * 60초 * 1000밀리초)

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration) // 만료 시간 설정
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();

        return token;
    }


    private void debugJwtToken(String token) {
        try {
            JwtParserBuilder parserBuilder = Jwts.parserBuilder();
            parserBuilder.setSigningKey(keyBytes); // keyBytes는 적절한 값으로 설정해야 함

            Jws<Claims> claimsJws = parserBuilder.build().parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            System.out.println("Decoded JWT claims:");
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Expiration: " + claims.getExpiration());
            // 필요한 클레임 정보를 추가로 출력

            System.out.println("JWT verification successful.");
        } catch (Exception e) {
            System.out.println("JWT verification failed: " + e.getMessage());
        }
    }

    private void debugEncodeJwtToken(String token) {
        String[] jwtParts = token.split("\\.");

        if (jwtParts.length == 3) {
            String encodedHeader = jwtParts[0];
            String encodedClaims = jwtParts[1];
            String encodedSignature = jwtParts[2];

            System.out.println("Encoded JWT parts:");
            System.out.println("Header: " + encodedHeader);
            System.out.println("Claims: " + encodedClaims);
            System.out.println("Signature: " + encodedSignature);
        } else {
            System.out.println("Invalid JWT format");
        }
    }




    @PostMapping("/signup")
    public ResponseEntity<String> sign(@RequestBody MemberEntity member) {
        try {
            MemberEntity createdMember = userService.createUser(member);
            return ResponseEntity.ok("Sign up successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}