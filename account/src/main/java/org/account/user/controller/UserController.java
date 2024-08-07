package org.account.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.account.user.domain.User;
import org.account.user.repository.UserRepository;
import org.account.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173") // 클라이언트 URL을 여기에 추가
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user, HttpServletRequest request) {
        User existingUser = userService.loginUser(user.getUserId(), user.getPassword());
        if (existingUser != null) {
            HttpSession session = request.getSession(true); // Session이 없으면 생성
            session.setAttribute("userId", existingUser.getUserId());
            session.setMaxInactiveInterval(1800); // 세션 유지 시간 30분
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 예외 기록
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Registration failed", e.getMessage())); // 사용자에게 에러 메시지 전달
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.getList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        Optional<User> user = userRepository.get(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
        user.setUserId(userId);
        try {
            User updateUser = userRepository.update(user);
            return ResponseEntity.ok(updateUser);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 예외 기록
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Update failed", e.getMessage())); // 사용자에게 에러 메시지 전달
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        int rowsAffected = userRepository.delete(userId);
        if (rowsAffected > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.noContent().build();
    }
}
