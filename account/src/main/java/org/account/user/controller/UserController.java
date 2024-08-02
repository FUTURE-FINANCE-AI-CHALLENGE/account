package org.account.user.controller;

import lombok.RequiredArgsConstructor;
import org.account.user.domain.User;
import org.account.user.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    public String index() {
        User user = new User("sdasd", "asdasd", "00000asd", "dsad", "dsad", "asd", "asd", "asd");
        userRepository.create(user);
        return "OK";
    }
}
