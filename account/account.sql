drop table users;
CREATE TABLE users (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY, -- ID 필드, 자동 증가하는 기본 키
                                        userId VARCHAR(255) UNIQUE NOT NULL, -- 사용자 ID, 유일 제약조건 추가
                                        password VARCHAR(255) NOT NULL, -- 비밀번호
                                        name VARCHAR(255) NOT NULL, -- 사용자 이름
                                        phoneNumber VARCHAR(20), -- 사용자 전화번호
                                        gender ENUM('M', 'F', 'Other') NOT NULL, -- 사용자 성별
                                        role VARCHAR(50), -- 사용자 역할
                                        email VARCHAR(255) UNIQUE NOT NULL, -- 이메일, 유일 제약조건 추가
                                        birthYear VARCHAR(20) -- 생년
                 );

drop table account;
ALTER TABLE users MODIFY birthYear VARCHAR(10);
CREATE TABLE account
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId      VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,

    description TEXT,
    date        DATE,
    FOREIGN KEY (userId) REFERENCES users (userId)
);



CREATE TABLE expense (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         accountId BIGINT NOT NULL,
                         amount INT,
                         FOREIGN KEY (accountId) REFERENCES account (id)
);

CREATE TABLE income (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        accountId BIGINT NOT NULL,
                        amount INT,
                        FOREIGN KEY (accountId) REFERENCES account (id)
);
ALTER TABLE account DROP FOREIGN KEY account_ibfk_1;
