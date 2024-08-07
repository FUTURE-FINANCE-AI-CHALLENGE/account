package org.account.service.domain;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Account {
    private Long id;
    private String userId;
    private String title;
    private String description;
    private Date date;
    private Integer amount;  // 금액 (수입 또는 지출)
    private String type;     // 트랜잭션 유형 (INCOME 또는 EXPENSE)
    private String category; // 카테고리 (예: "Salary", "Groceries")
}