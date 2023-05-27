package com.book.project.domain.Repository;

import com.book.project.domain.DTO.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@Slf4j
@Repository
public class UserRepository {
    private static Map<Long, User> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용
    public User save(User User) {
        User.setId(++sequence);
        log.info("save: member={}", User);
        store.put(User.getId(), User);
        return User;
    }
    public User findById(Long id) {
        return store.get(id);
    }
    public Optional<User> findByLoginId(String loginId) {

        //람다 스트림 사용 ()리스트를 =>stream으로 변환시켜 루프를 돌림
        return findAll().stream()

                //filter 조건에 만족할시
                .filter(m -> m.getLoginId().equals(loginId))

                //제일먼저 나오는 값 반환
                .findFirst();
    }
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
    public void deleteById(Long id) {
        store.remove(id);
    }
    public void clearStore() {
        store.clear();
    }
}