package org.account.service.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Expense {
    private Long id;
    private Long accountId;
    private Integer amount;
}