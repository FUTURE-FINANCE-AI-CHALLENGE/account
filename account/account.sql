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
    id          BIGINT AUTO_INCREMENT PRIMARY KEY, -- 계좌 ID, 자동 증가하는 기본 키
    userId      VARCHAR(255) NOT NULL,             -- 사용자 ID, 외래 키로 설정
    title       VARCHAR(255) NOT NULL,             -- 제목
    total       INT,                               -- 총 금액
    income      INT,                               -- 수입
    expense     INT,                               -- 지출
    category    VARCHAR(255),                      -- 카테고리
    description TEXT,                              -- 설명
    date        DATE,                              -- 날짜
    FOREIGN KEY (userId) REFERENCES users (userId) -- 외래 키 제약조건 설정
);

CREATE INDEX idx_userId ON Users(userId);
ALTER TABLE account DROP FOREIGN KEY account_ibfk_1;


describe table users;