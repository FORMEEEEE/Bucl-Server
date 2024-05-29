package com.freeder.buclserver.admin.후기관리.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class 리뷰관리Dto {

    private Long userId;
    private List<상품댓글Dto> product;


    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class 상품댓글Dto {

        private Long id;
        private String productName;
        private String content;
        private LocalDateTime createdAt;


    }

}

