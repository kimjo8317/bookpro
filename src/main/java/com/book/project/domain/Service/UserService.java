package com.book.project.domain.Service;

import com.book.project.domain.Entity.Member;
import com.book.project.domain.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Member login(int id, String password) {
        Member member = userRepository.findById(id);
        if (member != null && member.getPw().equals(password)) {
            return member;
        }
        return null;
    }
        public Member createUser(Member user) {
            return userRepository.save(user);
        }

        public Member getUserById(int id) {
            return userRepository.findById(id);
        }

}