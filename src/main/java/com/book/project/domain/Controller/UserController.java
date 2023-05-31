package com.book.project.domain.Controller;

import com.book.project.domain.DTO.Member;
import com.book.project.domain.Entity.MemberEntity;
import com.book.project.domain.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member Member) {
        Member member = userService.login(Member.getId(), Member.getPw());
        if (member != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 현재 로그인된 사용자 식별 또는 세션 정보 제거 로직을 구현합니다.
        // 예를 들어, 세션 관리를 사용하는 경우 세션을 무효화하거나 삭제할 수 있습니다.
        // 또는 토큰 기반 인증을 사용하는 경우, 사용자의 토큰을 무효화하거나 삭제할 수 있습니다.

        // 로그아웃 성공 시, 로그아웃 완료 메시지를 반환합니다.
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberEntity member) {
        try {
            MemberEntity createdMemberEntity = (MemberEntity) userService.createUser(member);
            return ResponseEntity.ok("Sign up successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
