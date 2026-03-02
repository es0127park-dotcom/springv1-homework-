package com.example.springv1.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_test() {
        // given
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        // then
        userRepository.save(user);

        // eye
        System.out.println("==============================================");
        System.out.println(user);
    }

    @Test
    public void findByUsername_test() {
        // given
        String username = "ssar";

        // then
        User user = userRepository.findByUsername(username).get();

        // eye
        System.out.println("==============================================");
        System.out.println(user);
    }
}
