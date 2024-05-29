package com.freeder.buclserver.admin.포인트.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class 회원정보Dto {

    private Long userId;

    private Long rewardWithDrawalId;

    private String userName;

    private String cellPhone;

    private String accountNum;

    private String bankName;

    private Integer  rewardWithdrawalAmount;



}