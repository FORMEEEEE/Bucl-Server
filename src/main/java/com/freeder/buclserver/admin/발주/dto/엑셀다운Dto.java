package com.freeder.buclserver.admin.발주.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class 엑셀다운Dto {

    private Long consumerOrderId;
    private Long productId;
    private Long shippingId;
    private String productName;
    private String productOptionValue;
    private int productOrderQty;
    private int rewardUseAmount;
    private int totalOrderAmount;
    private String consumerName;
    private String consumerAddress;
    private String consumerCellphone;
    private String shippingCoName;
    private String trackingNum;
    private LocalDateTime createAt;

}
