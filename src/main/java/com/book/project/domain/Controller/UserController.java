package com.book.project.domain.Controller;

import com.book.project.domain.DTO.User;
import com.book.project.domain.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository UserRepository;

    public UserController(UserRepository memberRepository) {
        this.UserRepository = memberRepository;
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User member) {
        return UserRepository.save(member);
    }

    @GetMapping("/{id}")
    public User getuserById(@PathVariable Long id) {
        return UserRepository.findById(id);
    }

    @GetMapping("/login/{loginId}")
    public Optional<User> getuserByLoginId(@PathVariable String loginId) {
        return UserRepository.findByLoginId(loginId);
    }

    @GetMapping("/findall")
    public List<User> getAllusers() {
        return UserRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteuser(@PathVariable Long id) {
        // Additional logic if needed before deleting
        UserRepository.deleteById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAllusers() {
        UserRepository.clearStore();
    }
}
