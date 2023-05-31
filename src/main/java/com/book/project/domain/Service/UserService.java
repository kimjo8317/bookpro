package com.book.project.domain.Service;
import com.book.project.domain.DTO.Member;
import org.mindrot.jbcrypt.BCrypt;
import com.book.project.domain.Entity.MemberEntity;
import org.springframework.stereotype.Service;
import com.book.project.domain.Repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //로그인
    public Member login(int id, String password) {
        Member member = userRepository.findById(id);
        if (member != null && member.getPw().equals(password)) {
            return member;
        }
        return null;
    }




    //회원가입
    public MemberEntity createUser(MemberEntity user) {
        // 입력값의 유효성 검사
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다.");
        }

        // 동일한 ID의 회원이 이미 존재하는지 확인
        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        // 비밀번호를 해싱하여 저장
        String hashedPassword = hashPassword(user.getPw());
        user.setPw(hashedPassword);

        // 회원 저장
        return userRepository.save(user);
    }
    public boolean isValidUser(MemberEntity user) {
        // 유효성 검사 로직을 구현합니다.
        // 예를 들어, 비밀번호의 길이를 확인할 수 있습니다.
        return isValidPassword(user.getPw());
    }

    public boolean isValidPassword(String password) {
        // 비밀번호 유효성 검사 로직을 구현합니다.
        // 예를 들어, 비밀번호의 최소 길이, 특수문자 포함 여부 등을 확인할 수 있습니다.
        return password.length() >= 8;
    }

    public String hashPassword(String password) {
        // 비밀번호를 해싱하여 반환하는 로직을 구현합니다.
        // 예를 들어, BCrypt 알고리즘 등을 사용하여 비밀번호를 해싱할 수 있습니다.
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}