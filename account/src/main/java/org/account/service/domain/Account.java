package org.account.service.domain;

import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Account {
    private Long id;
        private String userId;
        private String title;
        private Integer total;
        private Integer income;
        private Integer expense;
        private String category;
        private String description;
        private Date date;
    }


