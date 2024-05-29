package com.freeder.buclserver.admin.포인트.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class 포인트적립내역Dto {
    private String brandName;
    private String name;
    private Integer receivedRewardAmount;
    private Integer spentRewardAmount;
    private LocalDateTime createdAt;
}