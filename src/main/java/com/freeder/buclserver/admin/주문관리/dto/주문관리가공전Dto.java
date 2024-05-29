package com.freeder.buclserver.admin.주문관리.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class 주문관리가공전Dto {

    private Long consumerOrderId;
    private Long productId;
    private String name;
    private String address;
    private String addressDetail;
    private String recipientName;
    private String contactNumber;
    private LocalDateTime createdAt;



}
