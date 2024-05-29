package com.freeder.buclserver.admin.후기관리.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class 후기관리Dto {

    private Long userId;
    private List<상품댓글Dto> product;


    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class 상품댓글Dto {

        private Long id;
        private String productName;
        private String comment;
        private LocalDateTime createdAt;


    }

}
