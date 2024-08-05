package org.account.user.domain;

import lombok.*;
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Long id; // id 필드 추가
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String gender;
    private String role;
    private String email;
    private String birthYear;

    public User(Long id, String userId, String password, String name, String phoneNumber, String gender, String role, String email, String birthYear) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.email = email;
        this.birthYear = birthYear;
    }
}