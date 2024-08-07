package org.account.service.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;  // 수정: java.sql.Date -> java.util.Date

@Data
@Builder
public class Account {
    private Long id;
    private String userId;
    private String title;
    private String description;
    private Date date;  // 수정: java.sql.Date -> java.util.Date
    private Integer amount;
    private String type;
    private String category;
}
