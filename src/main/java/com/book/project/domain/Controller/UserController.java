package com.book.project.domain.Controller;

import com.book.project.domain.DTO.LoginRequest;
import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Entity.SubscribeEntity;
import com.book.project.domain.Entity.WithdrawnMember;
import com.book.project.domain.Repository.WithdrawnMemberRepository;
import com.book.project.domain.Service.SubscribeService;
import com.book.project.domain.Service.UserService;
import com.book.project.domain.Service.WithdrawnMemberService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jws;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private WithdrawnMemberService withdrawnMemberService;
    @Autowired
    private WithdrawnMemberRepository withdrawnMemberRepository;


    //회원정보 조회 API
    @GetMapping("/member/{id}")
    public ResponseEntity<?> getMemberInfo(@PathVariable("id") String memberId) {
        try {
            MemberEntity member = userService.getUserById(memberId);

            if (member != null) {
                // 회원 정보를 포함한 응답 생성
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode responseNode = objectMapper.createObjectNode();
                responseNode.put("id", member.getId());
                responseNode.put("name", member.getName());
                responseNode.put("subscribe", member.getSubscribe() != null);

                SubscribeEntity subscribe = member.getSubscribe();
                if (subscribe != null) {
                    LocalDateTime startDate = subscribe.getStartDate();
                    LocalDateTime endDate = subscribe.getEndDate();
                    responseNode.put("startDate", startDate != null ? startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A");
                    responseNode.put("endDate", endDate != null ? endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A");
                } else {
                    responseNode.put("startDate", "N/A");
                    responseNode.put("endDate", "N/A");
                }

                String responseJson = responseNode.toString();

                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(responseJson);
            } else {
                return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            boolean isAuthenticated = userService.authenticateUser(loginRequest.getId(), loginRequest.getPw());

            // 탈퇴 여부 확인
            boolean isWithdrawn = checkWithdrawnMember(loginRequest.getId());

            if (isWithdrawn) {
                // 탈퇴한 회원이면 로그인 거부
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Withdrawn member cannot login");
            }
            if (isAuthenticated) {
                // 인증 성공 - JWT 발급
                String token = generateJwtToken(loginRequest.getId());

                // 세션에 토큰 저장
                session.setAttribute("token", token);

                // 구독 여부 확인
                boolean isSubscribed = checkSubscription(loginRequest.getId());

                // 회원 정보 조회
                MemberEntity member = userService.getUserById(loginRequest.getId());

                if (member != null) {
                    // 회원 정보를 포함한 응답 생성
                    ObjectMapper objectMapper = new ObjectMapper();
                    ObjectNode responseNode = objectMapper.createObjectNode();
                    responseNode.put("token", token);
                    responseNode.put("message", "로그인 성공");
                    responseNode.put("id", member.getId());
                    responseNode.put("name", member.getName());
                    responseNode.put("subscribe", isSubscribed);
                    responseNode.put("confirm", member.isConfirm());

                    SubscribeEntity subscribe = member.getSubscribe();
                    if (subscribe != null) {
                        LocalDateTime startDate = subscribe.getStartDate();
                        LocalDateTime endDate = subscribe.getEndDate();
                        responseNode.put("startDate", startDate != null ? startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A");
                        responseNode.put("endDate", endDate != null ? endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A");
                    } else {
                        responseNode.put("startDate", "N/A");
                        responseNode.put("endDate", "N/A");
                    }

                    String responseJson = responseNode.toString();

                    return ResponseEntity.ok()
                            .header("Content-Type", "application/json")
                            .body(responseJson);
                } else {
                    return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
                }
            } else {
                // 인증 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
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

    private boolean checkSubscription(String memberId) {
        MemberEntity memberEntity = userService.getUserById(memberId);
        if (memberEntity != null) {
            SubscribeEntity subscription = subscribeService.getSubscriptionByMemberId(memberEntity.getIdx());
            if (subscription != null) {
                boolean isSubscribed = subscription.getConfirm();
                if (!isSubscribed) {
                    memberEntity.setConfirm(false); // member의 confirm 값을 0으로 설정
                    userService.updateUser(memberEntity); // member 업데이트
                }
                return isSubscribed;
            }
        }
        return false; // 구독 정보가 없는 경우 false 반환
    }


    private void debugJwtToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .parseClaimsJws(token);

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

    //탈퇴회원체크
    private boolean checkWithdrawnMember(String id) {
        WithdrawnMember withdrawnMember = withdrawnMemberRepository.findById(id);

        return withdrawnMember != null;
    }




    @PostMapping("/signup")
    public ResponseEntity<String> sign(@RequestBody MemberEntity member) {
        try {
            //탈퇴한회원 재가입불가 withdrawn_member 테이블에 데이터날아가기전까지
            boolean isWithdrawnMember = checkWithdrawnMember(member.getId());
            if (isWithdrawnMember) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Withdrawn member cannot sign up");
            }

            MemberEntity createdMember = userService.createUser(member);
            return ResponseEntity.ok("Sign up successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestHeader("Authorization") String token) {
        // 1. 토큰 검증 및 회원 ID 추출
        String memberId = verifyAndExtractMemberId(token);

        if (memberId != null) {
            // 2. 회원 ID를 이용하여 회원 정보 조회
            MemberEntity member = userService.getUserById(memberId);
            if (member != null) {
                // 3. 구독 정보 업데이트 또는 다른 필요한 작업 수행
                Long memberIdx = member.getIdx(); // 회원의 idx 가져오기

                // 4. Subscribe 테이블에 구독 정보 저장 또는 업데이트
                SubscribeEntity existingSubscription = subscribeService.getSubscriptionByMemberId(memberIdx);
                if (existingSubscription != null) {
                    // 이미 구독 정보가 있는 경우
                    if (!existingSubscription.getConfirm()) {
                        // confirm이 0인 경우에만 업데이트
                        existingSubscription.setConfirm(true);
                        subscribeService.saveSubscription(existingSubscription);
                    }
                } else {
                    // 구독 정보가 없는 경우 새로 생성
                    SubscribeEntity subscribeEntity = SubscribeEntity.builder()
                            .confirm(true)
                            .endDate(LocalDateTime.now().plusMonths(1))
                            .startDate(LocalDateTime.now())
                            .member(member)
                            .build();
                    subscribeService.saveSubscription(subscribeEntity);
                }

                return ResponseEntity.ok("구독이 성공적으로 처리되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("구독 정보 저장에 실패했습니다.");
            }
        }
        return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token) {
        String memberId = verifyAndExtractMemberId(token);

        if (memberId != null) {
            // 회원 정보 조회
            MemberEntity memberEntity = userService.getUserById(memberId);
            if (memberEntity != null) {
                // withdrawn_member 테이블에 회원 데이터 추가
                WithdrawnMember withdrawnMember = WithdrawnMember.builder()
                        .id(memberEntity.getId())
                        .pw(memberEntity.getPw())
                        .name(memberEntity.getName())
                        .likeIdx(memberEntity.getLikeIdx())
                        .confirm(memberEntity.isConfirm())
                        .withdrawnDate(LocalDateTime.now().plusDays(14))
                        .build();
                withdrawnMemberService.saveWithdrawnMember(withdrawnMember);

                // 회원 삭제
                userService.deleteUser(memberEntity);

                return ResponseEntity.ok("회원이 성공적으로 탈퇴 처리되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
            }
        }
        return ResponseEntity.badRequest().body("토큰 검증에 실패하였습니다.");
    }

    private String verifyAndExtractMemberId(String token) {
        try {
            // Bearer 키워드 제거
            String jwtToken = token.replace("Bearer ", "");

            // 토큰 검증
            Claims claims = Jwts.parser()
                    .setSigningKey(keyBytes) // 토큰 검증에 사용되는 키
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // 회원 ID 추출
            String memberId = claims.getSubject();

            return memberId;
        } catch (Exception e) {
            System.out.println("JWT verification failed: " + e.getMessage());
            return null;
        }
    }
}