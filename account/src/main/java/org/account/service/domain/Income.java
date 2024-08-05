package org.account.service.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Income {
    private Long id;
    private Long accountId;
    private Integer amount;
}