package com.freeder.buclserver.admin.주문관리.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class 주문관리Dto{

    // 중복인친구들

    private Long consumerOrderId;
    private Long productId;
    private String name;
    private List<주문관리주소Dto> shippingAddress;

    // 중복이 아닌친구들

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class 주문관리주소Dto{
        private String address;
        private String addressDetail;
        private String recipientName;
        private String contactNumber;
        private LocalDateTime createAt;


    }


}
