package org.account.service.domain;

import lombok.*;

import java.sql.Date;
import java.util.List;

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
    private String description;
    private Date date;
    private List<Expense> expenses;
    private List<Income> incomes;
}