package com.book.project.domain.Service;

import com.book.project.domain.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(int id, String password) {
        User user = userRepository.findById(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
        public User createUser(User user) {
            return userRepository.save(user);
        }

        public User getUserById(int id) {
            return userRepository.findById(id);
        }

}