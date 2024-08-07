package org.account.user.service;

import lombok.RequiredArgsConstructor;
import org.account.user.domain.User;
import org.account.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(User user) {
        // 비밀번호는 암호화할 수 있습니다. (여기서는 생략)
        return userRepository.create(user);
    }

    public User loginUser(String userId, String password) {
        User user = userRepository.get(userId).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUser(String userId) {
        return userRepository.get(userId).orElse(null);
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.get(loginId).isPresent();
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.getByNickname(nickname).isPresent();
    }
}
