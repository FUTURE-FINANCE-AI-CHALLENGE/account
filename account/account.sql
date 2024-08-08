-- Drop existing tables if they exist
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       userId VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       phoneNumber VARCHAR(20),
                       gender ENUM('M', 'F', 'Other') NOT NULL,
                       role VARCHAR(50),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       birthYear VARCHAR(10)
);


CREATE TABLE account (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         userId VARCHAR(255) NOT NULL,
                         t itle VARCHAR(255) NOT NULL,
                         amount INT NOT NULL,
                         type ENUM('INCOME', 'EXPENSE') NOT NULL,
                         category VARCHAR(255),
                         description TEXT,
                         date DATE,
                         FOREIGN KEY (userId) REFERENCES users(userId)
);

