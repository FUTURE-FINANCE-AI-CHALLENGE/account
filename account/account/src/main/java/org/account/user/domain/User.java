package org.account.user.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class User {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String gender;
    private String role;
    private String email;
    private String birthYear;

    public User(String id, String password, String name, String phoneNumber, String gender, String role, String email, String birthYear) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.email = email;
        this.birthYear = birthYear;
    }
}
