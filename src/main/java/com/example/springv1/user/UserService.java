package com.example.springv1.user;

import org.springframework.stereotype.Service;

import com.example.springv1.core.ex.Exception401;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.SaveDTO requestDTO) {
        // 중복 체크
        if (userRepository.findByUsername(requestDTO.getUsername()).isPresent()) {
            throw new Exception401("이미 존재하는 유저네임입니다.");
        }

        // 회원가입
        User user = requestDTO.toEntity();
        userRepository.save(user);
    }
}
